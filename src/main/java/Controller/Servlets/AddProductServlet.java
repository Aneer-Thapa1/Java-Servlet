package Controller.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import Controller.Database.DatabaseController;
import Model.ProductModel;
import Util.DroneUtils;

@WebServlet(asyncSupported = true, urlPatterns = { DroneUtils.ADD_PRODUCT })
@MultipartConfig(
    fileSizeThreshold = 2097152, // 2 MB
    maxFileSize = 10485760L,     // 10 MB
    maxRequestSize = 52428800L   // 50 MB
)
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DatabaseController dbController = new DatabaseController();

    public AddProductServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String productName = request.getParameter(DroneUtils.PRODUCT_NAME);
            String productDescription = request.getParameter(DroneUtils.PRODUCT_DESCRIPTION);
            Part productImage = request.getPart(DroneUtils.PRODUCT_IMAGE);
            int productPrice = Integer.parseInt(request.getParameter(DroneUtils.PRODUCT_PRICE)); // Safe parsing later
            String productBrand = request.getParameter(DroneUtils.PRODUCT_BRAND);
            String productAvailability = request.getParameter(DroneUtils.PRODUCT_AVAILABILITY);
            int batteryLife = Integer.parseInt(request.getParameter("batteryLife"));
            double maxRange = Double.parseDouble(request.getParameter("maxRange"));
            String cameraQuality = request.getParameter("cameraQuality");
            double weight = Double.parseDouble(request.getParameter("weight"));

            // Additional parameters if needed
            double controlRange = Double.parseDouble(request.getParameter("controlRange"));
            int flightTime = Integer.parseInt(request.getParameter("flightTime"));
            boolean hasGPS = Boolean.parseBoolean(request.getParameter("hasGPS"));
            String videoResolution = request.getParameter("videoResolution");
            String controllerCompatibility = request.getParameter("controllerCompatibility");

            // Input validation
            if (productName == null || productDescription == null || productImage == null || 
                productBrand == null || productAvailability == null) {
                response.getWriter().write("Missing product details.");
                return;
            }

            ProductModel productModel = new ProductModel( productName, productDescription, productImage, 
                                                        productPrice, productAvailability, productBrand, 
                                                        batteryLife, maxRange, cameraQuality, weight, 
                                                        controlRange, flightTime, hasGPS, 
                                                        videoResolution, controllerCompatibility);
            int productId = dbController.addProduct(productModel);
if(productId == 1) {
	response.sendRedirect(request.getContextPath()+"/AdminProductsServlet");
}

        } catch (NumberFormatException e) {
            response.getWriter().write("Error parsing numerical input: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().write("Server error while adding product: " + e.getMessage());
        }
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String productName = request.getParameter(DroneUtils.PRODUCT_NAME);
            String productDescription = request.getParameter(DroneUtils.PRODUCT_DESCRIPTION);
            Part productImage = request.getPart(DroneUtils.PRODUCT_IMAGE);
            int productPrice = Integer.parseInt(request.getParameter(DroneUtils.PRODUCT_PRICE)); // Safe parsing later
            String productBrand = request.getParameter(DroneUtils.PRODUCT_BRAND);
            String productAvailability = request.getParameter(DroneUtils.PRODUCT_AVAILABILITY);
            int batteryLife = Integer.parseInt(request.getParameter("batteryLife"));
            double maxRange = Double.parseDouble(request.getParameter("maxRange"));
            String cameraQuality = request.getParameter("cameraQuality");
            double weight = Double.parseDouble(request.getParameter("weight"));

            // Additional parameters if needed
            double controlRange = Double.parseDouble(request.getParameter("controlRange"));
            int flightTime = Integer.parseInt(request.getParameter("flightTime"));
            boolean hasGPS = Boolean.parseBoolean(request.getParameter("hasGPS"));
            String videoResolution = request.getParameter("videoResolution");
            String controllerCompatibility = request.getParameter("controllerCompatibility");

            // Input validation
            if (productName == null || productDescription == null || productImage == null || 
                productBrand == null || productAvailability == null) {
                response.getWriter().write("Missing product details.");
                return;
            }

            ProductModel productModel = new ProductModel( productName, productDescription, productImage, 
                                                        productPrice, productAvailability, productBrand, 
                                                        batteryLife, maxRange, cameraQuality, weight, 
                                                        controlRange, flightTime, hasGPS, 
                                                        videoResolution, controllerCompatibility);
            int productId = dbController.addProduct(productModel);
if(productId == 1) {
	response.sendRedirect(request.getContextPath()+"/AdminProductsServlet");
}

        } catch (NumberFormatException e) {
            response.getWriter().write("Error parsing numerical input: " + e.getMessage());
        } catch (Exception e) {
            response.getWriter().write("Server error while adding product: " + e.getMessage());
        }
    }
}
