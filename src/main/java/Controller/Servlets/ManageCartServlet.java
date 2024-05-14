package Controller.Servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import Controller.Database.DatabaseController;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ManageCartServlet", urlPatterns = {"/ManageCartServlet"})
public class ManageCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String updateId = request.getParameter("updateId");
        String deleteId = request.getParameter("deleteId");

        if (updateId != null && !updateId.isEmpty()) {
            // Handle updating cart item (e.g., change quantity)
            doPut(request, response);
        } else if (deleteId != null && !deleteId.isEmpty()) {
            // Redirect to doDelete method for delete action
            doDelete(request, response);
        }
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deleteId = request.getParameter("deleteId");
        if (deleteId != null && !deleteId.isEmpty()) {
            try {
                // Handle delete action here
                int cartId = Integer.parseInt(deleteId);
                // Assuming you have a method in your database controller to delete item from the cart
                if (dbController.removeFromCart(cartId)) {
                    // Set success message
                    request.setAttribute("successMessage", "Item removed from cart successfully.");
                } else {
                    // Set error message
                    request.setAttribute("errorMessage", "Failed to remove item from cart.");
                }
            } catch (NumberFormatException e) {
                // Handle parsing error
                request.setAttribute("errorMessage", "Invalid product ID format.");
            } catch (SQLException | ClassNotFoundException e) {
                // Handle database error
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            }
            // Forward the request to message.jsp
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        }
    }
}
