package Controller.Servlets;

import Controller.Database.DatabaseController;
import Model.MessageModel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitContactFormServlet")
public class SubmitContactFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseController dbController;

    public SubmitContactFormServlet() {
        super();
        dbController = new DatabaseController(); // Initialize your DatabaseController
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        // Get the user email from the session
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user_mail");

        try {
            // Get the userId using the email
            int userId = dbController.getUserIdByEmail(userEmail);

            // Create the MessageModel instance
            MessageModel messageModel = new MessageModel(userId, name, email, subject, message);

            // Insert the message into the database
            int messageId = dbController.insertMessage(messageModel);

            if (messageId > 0) {
                // Redirect to a success page or back to the contact form with a success message
                request.setAttribute("message", "Message sent successfully.");
                request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
            } else {
                // Redirect to an error page or back to the contact form with an error message
                request.setAttribute("message", "Failed to send the message.");
                request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to an error page or back to the contact form with an error message
            request.setAttribute("message", "An error occurred while sending the message.");
            request.getRequestDispatcher("/pages/contact.jsp").forward(request, response);
        }
    }
}