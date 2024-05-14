<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Dynamic Nav Bar</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheet/navbar.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<!-- Ensure your FontAwesome kit is correct -->
</head>
<body>
	<nav>
		<div class="logo">
			<img src="<%=request.getContextPath()%>/images/whitelogo.png"
				alt="Logo">
		</div>
		<div class="nav-items">
			<ul>
				<li><a href=""
					class="<%="home".equals(request.getAttribute("currentPage")) ? "active" : ""%>">Home</a></li>
				<li><a href=""
					class="<%="collections".equals(request.getAttribute("currentPage")) ? "active" : ""%>">Collections</a></li>
			</ul>
		</div>
		<div class="shortcuts">
			<i class="fa-solid fa-user"></i> <i class="fa-solid fa-cart-shopping"></i>
		</div>
	</nav>
</body>
</html>
