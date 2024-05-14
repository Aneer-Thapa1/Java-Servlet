package Controller.Servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Controller.Database.DatabaseController;
import Model.OrderModel;
import Util.DroneUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = {DroneUtils.ORDER_SERVLET})
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    DatabaseController dbController = new DatabaseController();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_mail") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String userEmail = (String) session.getAttribute("user_mail");
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity")); // Retrieve quantity from the request
            String orderStatus = "pending";
            try {
                int userId = dbController.getUserIdByEmail(userEmail);

                // Process the order placement with quantity
                dbController.placeOrder(userId, orderStatus, productId, quantity); // Assumes this method is updated to handle quantity

                // Assuming the cart is cleared after the order is placed
                dbController.removeFromCart(cartId);

                // Set success message and redirect to the success page
                request.setAttribute("message", "Order placed successfully.");
                request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException e) {
                // Handle database and class not found errors
                request.setAttribute("message", "Failed to place order: " + e.getMessage());
                request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Handle parsing errors
            request.setAttribute("message", "Invalid input for cart ID, product ID, or quantity.");
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        }
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
       

        String userEmail = (String) session.getAttribute("user_mail");
        try {
        	 int userId = dbController.getUserIdByEmail(userEmail);
        	 
            // Retrieve orders for the logged-in user
            List<OrderModel> orders = dbController.fetchOrders(userId);
            

            // Forward the orders to a JSP page for display
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/pages/userOrder.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            // Handle database and class not found errors
            request.setAttribute("message", "Failed to retrieve orders: " + e.getMessage());
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        }
    }
}
