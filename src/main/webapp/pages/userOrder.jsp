<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.OrderModel"%>
<%
request.getSession(false);
if (session.getAttribute("admin_mail") != null && session.getAttribute("user_mail") == null) {
	response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Orders</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
	integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/stylesheet/userOrder.css">
</head>
<body>
	<nav>
		<div class="logo">
			<img src="<%=request.getContextPath()%>/images/logo.png" alt="">
		</div>

		<div class="nav-items">
			<ul>
				<li><a href="<%=request.getContextPath()%>/HomeServlet">Home</a></li>
				<li><a href="<%=request.getContextPath()%>/OrderServlet">Orders</a></li>
				<li><a href="<%=request.getContextPath()%>/pages/contact.jsp">Contact</a></li>

			</ul>
		</div>


		<div class="shortcuts">
			<i class="fa-solid fa-user"></i> <i class="fa-solid fa-cart-shopping"></i>
			<form action="${pageContext.request.contextPath}/UserLogout"
				method="post">
				<button type="submit"
					style="background-color: black; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;">
					LogOut</button>
			</form>
		</div>


	</nav>

<h2>Recent Purchases</h2>
	<div class="recent_purchase">
		
		<%
		List<OrderModel> orders = (List<OrderModel>) request.getAttribute("orders");
		if (orders != null && !orders.isEmpty()) {
			for (OrderModel order : orders) {
				double totalPrice = order.getProductPrice() * order.getQuantity(); // Calculate total price
		%>
		<div class="r_purchase">
			<div class="one_detail">
				<span><%=order.getProductName()%></span> <span>Date: <%=order.getOrderDate()%></span>
				<span>Quantity: <%=order.getQuantity()%></span> <span>Price:
					$<%=order.getProductPrice()%></span> <span>Total Price: $<%=totalPrice%></span>
				<span>Status: <%=order.getOrderStatus()%></span>
			</div>
		</div>
		<%
		}
		} else {
		%>
		<p>No recent purchases found.</p>
		<%
		}
		%>
	</div>
</body>
</html>
