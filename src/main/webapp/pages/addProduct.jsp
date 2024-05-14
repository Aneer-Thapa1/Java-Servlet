<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") == null && session.getAttribute("user_mail") != null){
		response.sendRedirect(request.getContextPath()+"/manageDisplayServlet");
	}

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Product</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/addProduct.css"> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

<nav>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="">
        </div>
        <div class="nav-items">
            <ul>
                <li><a href="${pageContext.request.contextPath}/DisplayMessageServlet">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/pages/addProduct.jsp">Add Product</a></li>
                <li><a href="${pageContext.request.contextPath}/AdminProductsServlet">View Products</a></li>
                <li><a href="${pageContext.request.contextPath}/AdminOrderServlet">View Orders</a></li>
            </ul>
        </div>
        
        <form action="${pageContext.request.contextPath}/UserLogout" method = "post" >
         <button type="submit" style="background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;">
        LogOut
    </button>
        </form>
    </nav>
    <div class="product-form-container">
        <form action="${pageContext.request.contextPath}/AddProductServlet" method="post" enctype="multipart/form-data">
            <h1>Add New Product</h1>
            
            <div class="form-group">
                <div class="input-container">
                    <label for="productName">Product Name:</label>
                    <input type="text" id="productName" name="productName" required>
                </div>
                <div class="input-container">
                    <label for="productDescription">Product Description:</label>
                    <textarea id="productDescription" name="productDescription" required></textarea>
                </div>
                <div class="input-container">
                    <label for="productImage">Product Image:</label>
                    <input type="file" id="productImage" name="productImage" required>
                </div>
            </div>

            <div class="form-group">
                <div class="input-container">
                    <label for="productPrice">Product Price:</label>
                    <input type="number" id="productPrice" name="productPrice" required>
                </div>
                <div class="input-container">
                    <label for="productBrand">Product Brand:</label>
                    <input type="text" id="productBrand" name="productBrand" required>
                </div>
                <div class="input-container">
                    <label for="productAvailability">Product Availability:</label>
                    <input type="number" id="productAvailability" name="productAvailability" required>
                </div>
            </div>

            <div class="form-group">
                <div class="input-container">
                    <label for="batteryLife">Battery Life (minutes):</label>
                    <input type="number" id="batteryLife" name="batteryLife" required>
                </div>
                <div class="input-container">
                    <label for="maxRange">Max Range (km):</label>
                    <input type="number" id="maxRange" name="maxRange" step="0.1" required>
                </div>
                <div class="input-container">
                    <label for="cameraQuality">Camera Quality (megapixels):</label>
                    <input type="text" id="cameraQuality" name="cameraQuality" required>
                </div>
            </div>

            <div class="form-group">
                <div class="input-container">
                    <label for="weight">Weight (kg):</label>
                    <input type="number" id="weight" name="weight" step="0.1" required>
                </div>
                <div class="input-container">
                    <label for="controlRange">Control Range (meters):</label>
                    <input type="number" id="controlRange" name="controlRange" required>
                </div>
                <div class="input-container">
                    <label for="flightTime">Flight Time (minutes):</label>
                    <input type="number" id="flightTime" name="flightTime" required>
                </div>
            </div>

            <div class="form-group">
                <div class="input-container">
                    <label for="hasGPS">Has GPS:</label>
                    <select id="hasGPS" name="hasGPS">
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                    </select>
                </div>
                <div class="input-container">
                    <label for="videoResolution">Video Resolution:</label>
                    <input type="text" id="videoResolution" name="videoResolution" required>
                </div>
                <div class="input-container">
                    <label for="controllerCompatibility">Controller Compatibility:</label>
                    <input type="text" id="controllerCompatibility" name="controllerCompatibility" required>
                </div>
            </div>

            <button type="submit">Add Product</button>
        </form>
    </div>
    <script src="<%=request.getContextPath()%>/path/to/your/js/bootstrap.min.js"></script>
</body>
</html>
