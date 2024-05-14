<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.ProductModel"%>
<%@ page import="Controller.Database.DatabaseController"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Details</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/stylesheet/singleProduct.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<%
request.getSession(false);
if (session.getAttribute("admin_mail") != null && session.getAttribute("user_mail") == null) {
	response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
}
%>
</head>
<body>

	<nav>
		<div class="logo">
			<img src="<%=request.getContextPath()%>/images/logo.png" alt="">
		</div>

		<div class="nav-items">
			<ul>
				 <li><a href="<%=request.getContextPath()%>/HomeServlet" class="active">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/OrderServlet">Orders</a></li>
                <li><a href="<%=request.getContextPath()%>/pages/contact.jsp">Contact</a></li>

			</ul>
		</div>


	 <div class="shortcuts">
            <a href="${pageContext.request.contextPath}/pages/profile.jsp"><i class="fa-solid fa-user"></i></a>
            <a href="${pageContext.request.contextPath}/CartServlet"><i class="fa-solid fa-cart-shopping"></i></a>
            <form action="${pageContext.request.contextPath}/UserLogout" method="post">
                <button type="submit" style="background-color: black; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;">
                    LogOut
                </button>
            </form>
        </div>


	</nav>


	<%
	String productId = request.getParameter("id");
	DatabaseController dbController = new DatabaseController();
	ProductModel product = null;

	if (productId != null && !productId.isEmpty()) {
		try {
			product = dbController.getProductById(Integer.parseInt(productId));
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Error retrieving product details.");
			return; // Stop further execution in case of error
		}
	}

	if (product != null) {
	%>
	<!-- Product Description -->
	<div class="prod_desc">
		<img
			src="<%=request.getContextPath()%>/resources/images/<%=product.getProductImage()%>"
			alt="<%=product.getProductName()%>">
		<div class="prod_info">
			<h1><%=product.getProductName()%></h1>
			<p>Specifications:</p>
			<div class="speci">
				<span>Flight Time: <%=product.getFlightTime()%> min
				</span> <span>Range: <%=product.getMaxRange()%> km
				</span> <span>Camera: <%=product.getCameraQuality()%></span> <span>Battery
					Life: <%=product.getBatteryLife()%> hours
				</span> <span>Weight: <%=product.getWeight()%> kg
				</span> <span>Control Range: <%=product.getControlRange()%> km
				</span> <span>GPS: <%=product.isHasGPS() ? "Yes" : "No"%></span> <span>Video
					Resolution: <%=product.getVideoResolution()%></span> <span>Controller
					Compatibility: <%=product.getControllerCompatibility()%></span>
			</div>
			<div class="prod_btns">
				<form action="${pageContext.request.contextPath}/CartServlet"
					method="POST">
					<input type="hidden" name="productId"
						value="<%=product.getProductId()%>"> <input type="hidden"
						name="quantity" value="1">
					<!-- Assuming a default quantity of 1 -->
					<button type="submit" class="cart-btn">
						Add to Cart <i class="fa-solid fa-cart-shopping"></i>
					</button>
				</form>
			</div>
		</div>
	</div>
	<%
	} else {
	out.println("<p>Product not found.</p>");
	}
	%>
</body>
</html>
