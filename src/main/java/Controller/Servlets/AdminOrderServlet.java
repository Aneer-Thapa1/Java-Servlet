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

@WebServlet(asyncSupported = true, urlPatterns = { "/AdminOrderServlet" })
public class AdminOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        

     

        try {
            // Retrieve all orders for admin
            List<OrderModel> orders = dbController.fetchAllOrders();

            // Forward the orders to a JSP page for display
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/pages/adminOrders.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            // Handle database and class not found errors
            request.setAttribute("message", "Failed to retrieve orders: " + e.getMessage());
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        }
    }
}
