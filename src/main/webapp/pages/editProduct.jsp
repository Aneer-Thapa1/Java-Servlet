<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.ProductModel"%>
<%@ page import="Controller.Database.DatabaseController"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/editProduct.css">
</head>
<body>
<%
    ProductModel product = (ProductModel) request.getAttribute("product");
%>

 <div>
        <p>Debug: Product ID = <%=product.getProductImage()%></p>
    </div>

    <div class="product-form-container">
        <h1>Edit Product</h1>
        <form action="<%=request.getContextPath()%>/EditProductServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" id="productId" name="productId" value="<%=product.getProductId()%>" readonly>
          
            
            <div class="form-row">
                <div class="form-group">
                    <label for="productName">Product Name:</label>
                    <input type="text" id="productName" name="productName" value="<%=product.getProductName()%>" required>
                </div>
                <div class="form-group">
                    <label for="productDescription">Product Description:</label>
                    <textarea id="productDescription" name="productDescription" required><%=product.getProductDescription()%></textarea>
                </div>
                <div class="form-group">
                    <label for="productImage">Product Image:</label>
                    <input type="file" id="productImage" name="productImage" value="<%=product.getProductImage()%>">
                    <img src="<%=request.getContextPath()%>/resources/images/<%=product.getProductImage()%>" alt="<%=product.getProductName()%>" width="100">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="productPrice">Product Price:</label>
                    <input type="number" id="productPrice" name="productPrice" value="<%=product.getProductPrice()%>" required>
                </div>
                <div class="form-group">
                    <label for="productBrand">Product Brand:</label>
                    <input type="text" id="productBrand" name="productBrand" value="<%=product.getProductBrand()%>" required>
                </div>
                <div class="form-group">
                    <label for="productAvailability">Product Availability:</label>
                    <input type="text" id="productAvailability" name="productAvailability" value="<%=product.getProductAvailability()%>" required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="batteryLife">Battery Life (minutes):</label>
                    <input type="number" id="batteryLife" name="batteryLife" value="<%=product.getBatteryLife()%>" required>
                </div>
                <div class="form-group">
                    <label for="maxRange">Max Range (km):</label>
                    <input type="number" id="maxRange" name="maxRange" value="<%=product.getMaxRange()%>" required>
                </div>
                <div class="form-group">
                    <label for="cameraQuality">Camera Quality (megapixels):</label>
                    <input type="number" id="cameraQuality" name="cameraQuality" value="<%=product.getCameraQuality()%>" required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="weight">Weight (kg):</label>
                    <input type="number" id="weight" name="weight" value="<%=product.getWeight()%>" required>
                </div>
                <div class="form-group">
                    <label for="controlRange">Control Range (meters):</label>
                    <input type="number" id="controlRange" name="controlRange" value="<%=product.getControlRange()%>" required>
                </div>
                <div class="form-group">
                    <label for="flightTime">Flight Time (minutes):</label>
                    <input type="number" id="flightTime" name="flightTime" value="<%=product.getFlightTime()%>" required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="hasGPS">Has GPS:</label>
                    <select id="hasGPS" name="hasGPS">
                        <option value="true" <%=product.isHasGPS() ? "selected" : ""%>>Yes</option>
                        <option value="false" <%=!product.isHasGPS() ? "selected" : ""%>>No</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="videoResolution">Video Resolution:</label>
                    <input type="text" id="videoResolution" name="videoResolution" value="<%=product.getVideoResolution()%>" required>
                </div>
                <div class="form-group">
                    <label for="controllerCompatibility">Controller Compatibility:</label>
                    <input type="text" id="controllerCompatibility" name="controllerCompatibility" value="<%=product.getControllerCompatibility()%>" required>
                </div>
            </div>
            
            <button type="submit">Save Changes</button>
        </form>
    </div>
</body>
</html>
