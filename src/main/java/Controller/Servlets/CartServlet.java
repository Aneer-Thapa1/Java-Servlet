package Controller.Servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Controller.Database.DatabaseController;
import Model.CartItemModel;
import Util.DroneUtils;

import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = {DroneUtils.CART_SERVLET})
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DatabaseController dbController = new DatabaseController();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user_mail"); // Retrieve user email from session

        try {
            int userId = dbController.getUserIdByEmail(userEmail);
            Integer productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = 1;

            int result = dbController.addToCart(userId, productId, quantity);
            switch (result) {
                case 0: // Success
                    request.setAttribute("messageTitle", "Success");
                    request.setAttribute("message", "Item successfully added to your cart!");
                    break;
                case 1: // Already exists
                    request.setAttribute("messageTitle", "Notice");
                    request.setAttribute("message", "Item already exists in your cart.");
                    break;
                default: // Error
                    request.setAttribute("messageTitle", "Error");
                    request.setAttribute("message", "An unexpected error occurred.");
                    break;
            }
            request.setAttribute("backLink", request.getContextPath() + "/pages/home.jsp");
        } catch (NumberFormatException | SQLException e) {
            request.setAttribute("messageTitle", "Error");
            request.setAttribute("message", "Error processing request: " + e.getMessage());
        } catch (Exception e) {
            request.setAttribute("messageTitle", "Error");
            request.setAttribute("message", "An unexpected error occurred due to an internal issue.");
            e.printStackTrace(); // Log to console or log files
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/message.jsp");
            dispatcher.forward(request, response);
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user_mail");

   
        try {
            int userId = dbController.getUserIdByEmail(userEmail); // Fetch userId using email
            List<CartItemModel> cartItems = dbController.getCartItems(userId);
            request.setAttribute("cartItems", cartItems);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/cart.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
          
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            
        }
    }
}
