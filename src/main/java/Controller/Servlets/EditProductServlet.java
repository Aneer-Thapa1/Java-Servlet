package Controller.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Controller.Database.DatabaseController;
import Model.ProductModel;
import Util.DroneUtils;

@WebServlet(urlPatterns = {"/EditProductServlet"})
@MultipartConfig(
    fileSizeThreshold = 2097152, // 2 MB
    maxFileSize = 10485760L,     // 10 MB
    maxRequestSize = 52428800L   // 50 MB
)
public class EditProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DatabaseController dbController = new DatabaseController();

    public EditProductServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.getWriter().println("No product selected.");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            ProductModel product = dbController.getProductById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/pages/editProduct.jsp").forward(request, response);
            } else {
                response.getWriter().println("Product not found.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid product ID.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            String productName = request.getParameter("productName");
            String productDescription = request.getParameter("productDescription");
            int productPrice = Integer.parseInt(request.getParameter("productPrice"));
            String productAvailability = request.getParameter("productAvailability");
            String productBrand = request.getParameter("productBrand");
            int batteryLife = Integer.parseInt(request.getParameter("batteryLife"));
            double maxRange = Double.parseDouble(request.getParameter("maxRange"));
            String cameraQuality = request.getParameter("cameraQuality");
            double weight = Double.parseDouble(request.getParameter("weight"));
            double controlRange = Double.parseDouble(request.getParameter("controlRange"));
            int flightTime = Integer.parseInt(request.getParameter("flightTime"));
            boolean hasGPS = Boolean.parseBoolean(request.getParameter("hasGPS"));
            String videoResolution = request.getParameter("videoResolution");
            String controllerCompatibility = request.getParameter("controllerCompatibility");

            Part filePart = request.getPart("productImage");

            ProductModel existingProduct = dbController.getProductById(productId);
            if (existingProduct == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                return;
            }

            String productImage = existingProduct.getProductImage();
            if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                ProductModel tempProduct = new ProductModel();
                productImage = tempProduct.saveImageToDisk(filePart); // Save new image and get its path
            }

            ProductModel product = new ProductModel(productName, productDescription, filePart,
                    productPrice, productAvailability, productBrand, batteryLife,
                    maxRange, cameraQuality, weight, controlRange, flightTime, hasGPS,
                    videoResolution, controllerCompatibility);

            product.setProductId(productId); // Set the productId for the existing product
            product.setProductImage(productImage); // Use the potentially updated image path

            dbController.updateProduct(productId, product); // Ensure this method in DatabaseController takes productId as an argument
            response.sendRedirect(request.getContextPath() + DroneUtils.ADMIN_PRODUCTS_SERVLET);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numerical value.");
        } catch (IOException | ServletException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error while processing request.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update product due to invalid input or server error.");
        }
    }
}
