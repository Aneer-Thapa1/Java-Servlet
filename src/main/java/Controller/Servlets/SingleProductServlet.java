package Controller.Servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import Model.ProductModel;
import Util.DroneUtils;
import Controller.Database.DatabaseController;

@WebServlet(asyncSupported = true, urlPatterns = { DroneUtils.SINGLE_PRODUCT })
public class SingleProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("id");
        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                DatabaseController dbController = new DatabaseController();
                ProductModel product = dbController.getProductById(productId);

                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/view/singleProduct.jsp").forward(request, response);
                } else {
                    response.sendRedirect("errorPage.jsp?message=Product Not Found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("errorPage.jsp?message=Invalid Product ID");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("errorPage.jsp?message=Internal Server Error");
            }
        } else {
            response.sendRedirect("errorPage.jsp?message=No Product ID Provided");
        }
    }
}
