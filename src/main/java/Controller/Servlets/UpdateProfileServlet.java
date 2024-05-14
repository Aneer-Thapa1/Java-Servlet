package Controller.Servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import Controller.Database.DatabaseController;
import Model.UserModel;
import Util.DroneUtils;

@WebServlet(name = "UpdateProfileServlet", urlPatterns = {DroneUtils.UPDATE_PROFILE})
public class UpdateProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user_mail");

        System.out.println("Session User Email: " + userEmail);

        try {
            int userId = dbController.getUserIdByEmail(userEmail);
            if (userId == -1) {
                throw new Exception("User not found with email: " + userEmail);
            }

            System.out.println("User ID Retrieved: " + userId);

            // Retrieve form data
            String userName = request.getParameter("userName");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phoneNumber");

            System.out.println("Form Data - Username: " + userName);
            System.out.println("Form Data - Email: " + email);
            System.out.println("Form Data - Address: " + address);
            System.out.println("Form Data - Phone: " + phoneNumber);

            UserModel user = new UserModel(userName, email, address, null, phoneNumber, "user");
            user.setUserId(userId); // Set the user ID retrieved from the database

            boolean updateSuccess = dbController.updateUserData(user);
            if (updateSuccess) {
                System.out.println("Update Successful, Redirecting...");
                response.sendRedirect(request.getContextPath() + "/pages/message.jsp"); // Redirect on successful update
            } else {
                throw new Exception("Database update failed - No rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print the full stack trace to logs
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }
}
