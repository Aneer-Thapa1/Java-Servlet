package Controller.Servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Controller.Database.DatabaseController;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(asyncSupported = true, urlPatterns = { "/CompleteOrderServlet" })
public class CompleteOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdStr);
            dbController.markOrderAsCompleted(orderId);
            response.sendRedirect(request.getContextPath() + "/AdminOrderServlet");
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            request.setAttribute("message", "Failed to mark order as completed: " + e.getMessage());
            request.getRequestDispatcher("/pages/message.jsp").forward(request, response);
        }
    }
}
