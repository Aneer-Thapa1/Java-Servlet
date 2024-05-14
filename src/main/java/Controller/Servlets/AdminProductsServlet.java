package Controller.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.Database.DatabaseController;
import Model.ProductModel;
import Util.DroneUtils;

@WebServlet(asyncSupported = true, urlPatterns = {DroneUtils.ADMIN_PRODUCTS_SERVLET})
public class AdminProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    public AdminProductsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	try {
            // Fetch all product details from the database
            List<ProductModel> products = dbController.getAllProducts();
            

            // Set the list of products as an attribute in the request object
            req.setAttribute("products", products);

            // Forward the request to the manageProducts.jsp page
            req.getRequestDispatcher("/pages/manageProducts.jsp").forward(req, resp);
           
        
        } catch (Exception e) {
            // Log the exception (you can use a logging framework)
            e.printStackTrace(); // Replace with logging

            // Instead of redirecting to an error page, set error message and stay on the manageProducts page
            req.setAttribute("errorMessage", "Error processing request: " + e.getMessage());

            // Use the same dispatcher to manageProducts.jsp with an error message
            RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/manageProducts.jsp");
            dispatcher.forward(req, resp);
        }
        // Optional: You can implement doGet if you want to handle GET requests differently
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	try {
            // Fetch all product details from the database
            List<ProductModel> products = dbController.getAllProducts();
            

            // Set the list of products as an attribute in the request object
            req.setAttribute("products", products);

            // Forward the request to the manageProducts.jsp page
            req.getRequestDispatcher("/pages/manageProducts.jsp").forward(req, resp);
           
        
        } catch (Exception e) {
            // Log the exception (you can use a logging framework)
            e.printStackTrace(); // Replace with logging

            // Instead of redirecting to an error page, set error message and stay on the manageProducts page
            req.setAttribute("errorMessage", "Error processing request: " + e.getMessage());

            // Use the same dispatcher to manageProducts.jsp with an error message
            RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/manageProducts.jsp");
            dispatcher.forward(req, resp);
        }
        // Optional: You can implement doGet if you want to handle GET requests differently
    }
        
    
}