package Model;

import javax.servlet.http.Part;

import Util.DroneUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ProductModel {
    private int productId; 
    private String productName;
    private String productDescription;
    private String productImage;
    private int productPrice;
    private String productAvailability;
    private String productBrand;
    private int batteryLife; 
    private double maxRange;
    private String cameraQuality; 
    private double weight; 
    private double controlRange; 
    private int flightTime; 
    private boolean hasGPS; 
    private String videoResolution; 
    private String controllerCompatibility; 

    // Constructor for initializing with all properties
    public ProductModel(String productName, String productDescription, Part imagePart, 
                        int productPrice, String productAvailability, String productBrand, 
                        int batteryLife, double maxRange, String cameraQuality, double weight,
                        double controlRange, int flightTime, boolean hasGPS, 
                        String videoResolution, String controllerCompatibility) {
     
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = saveImageToDisk(imagePart);
        this.productPrice = productPrice;
        this.productAvailability = productAvailability;
        this.productBrand = productBrand;
        this.batteryLife = batteryLife;
        this.maxRange = maxRange;
        this.cameraQuality = cameraQuality;
        this.weight = weight;
        this.controlRange = controlRange;
        this.flightTime = flightTime;
        this.hasGPS = hasGPS;
        this.videoResolution = videoResolution;
        this.controllerCompatibility = controllerCompatibility;
    }

    // Default constructor
    public ProductModel() {
        // Default constructor if needed
    }

    // Method to save image to disk and return the filename
    public String saveImageToDisk(Part imagePart) {
        // Define the directory path where images will be saved.
        Path directory = Paths.get(DroneUtils.IMAGE_DIR);
        try {
            // Ensure the directory exists; create it if it doesn't.
            if (Files.notExists(directory)) {
                Files.createDirectories(directory);
            }

            // Generate a unique filename for the uploaded file to avoid name collisions.
            String fileName = UUID.randomUUID().toString() + "_" + extractFileName(imagePart);
            Path filePath = directory.resolve(fileName);

            // Save the uploaded file to the specified path on the disk.
            Files.copy(imagePart.getInputStream(), filePath);

            // Return the filename that will be saved in the database.
            return fileName;
        } catch (IOException e) {
            // Log the exception for debugging purposes.
            e.printStackTrace();

            // Return a default image filename if an error occurs during file saving.
            return "default.jpg";
        }
    }

    // Helper method to extract the filename from a Part object
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 2, content.length() - 1);
            }
        }
        return null;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(String productAvailability) {
        this.productAvailability = productAvailability;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    public String getCameraQuality() {
        return cameraQuality;
    }

    public void setCameraQuality(String cameraQuality) {
        this.cameraQuality = cameraQuality;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getControlRange() {
        return controlRange;
    }

    public void setControlRange(double controlRange) {
        this.controlRange = controlRange;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public boolean isHasGPS() {
        return hasGPS;
    }

    public void setHasGPS(boolean hasGPS) {
        this.hasGPS = hasGPS;
    }

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public String getControllerCompatibility() {
        return controllerCompatibility;
    }

    public void setControllerCompatibility(String controllerCompatibility) {
        this.controllerCompatibility = controllerCompatibility;
    }
}
