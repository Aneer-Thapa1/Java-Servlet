<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%
request.getSession(false);
if (session.getAttribute("admin_mail") != null && session.getAttribute("user_mail") == null) {
	response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
}
%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contact Us</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
	integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f7f7f7;
	color: #333;
	margin: 0;
	padding: 0;
	display: flex;
	flex-direction: column;
	align-items: center;
	height: 100vh;
}

nav {
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	width: 100%;
	height: 70px;
	background: #fff;
	box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

nav .logo img {
	width: 150px;
	height: 50px;
}

nav ul {
	display: flex;
	gap: 30px;
	list-style: none;
	cursor: pointer;
	margin: 0;
	padding: 0;
}

nav ul li {
	font-size: 16px;
	transition: all ease-in-out 0.6s;
}

nav ul li a {
	text-decoration: none;
	color: black;
	position: relative;
	font-weight: 600;
}

nav ul li a::after {
	content: "";
	width: 100%;
	height: 3px;
	background-color: black;
	position: absolute;
	top: 100%;
	left: 0;
	transform: scaleX(0);
	transition: transform 0.6s ease;
}

nav ul li a:hover::after {
	transform: scaleX(1);
}

.shortcuts {
	display: flex;
	gap: 30px;
	justify-content: center;
	align-items: center;
	font-size: 18px;
}

.shortcuts a {
	color: black;
	font-weight: 600;
	cursor: pointer;
}

.contact-form-container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: calc(100vh - 70px); /* Full viewport height minus nav height */
	width: 100%;
}

.contact-form {
	background-color: #fff;
	border-radius: 10px;
	padding: 30px;
	width: 100%;
	max-width: 500px;
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.contact-form h2 {
	text-align: center;
	margin-bottom: 20px;
	color: #333;
}

.contact-form .form-group {
	margin-bottom: 15px;
}

.contact-form label {
	display: block;
	margin-bottom: 5px;
	color: #666;
}

.contact-form input, .contact-form textarea {
	resize: none;
	width: 100%;
	padding: 10px;
	background-color: #f9f9f9;
	border: 1px solid #ddd;
	border-radius: 5px;
	color: #333;
	font-size: 16px;
	outline: none;
	transition: border-color 0.3s;
}

.contact-form input:focus, .contact-form textarea:focus {
	border-color: #888;
}

.contact-form button {
	width: 100%;
	padding: 10px;
	background-color: #333;
	border: none;
	border-radius: 5px;
	color: #fff;
	font-size: 18px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.contact-form button:hover {
	background-color: #555;
}
</style>
</head>

<body>
	<nav>
		<div class="logo">
			<img src="<%=request.getContextPath()%>/images/logo.png" alt="Logo">
		</div>
		<div class="nav-items">
			<ul>
				<li><a href="<%=request.getContextPath()%>/HomeServlet">Home</a></li>
				<li><a href="<%=request.getContextPath()%>/OrderServlet">Orders</a></li>
				<li><a href="<%=request.getContextPath()%>/pages/contact.jsp">Contact</a></li>
			</ul>
		</div>
		<div class="shortcuts">
			<a href="${pageContext.request.contextPath}/pages/profile.jsp"
				class="active"><i class="fa-solid fa-user"></i></a> <a
				href="${pageContext.request.contextPath}/CartServlet"><i
				class="fa-solid fa-cart-shopping"></i></a>
			<form action="${pageContext.request.contextPath}/UserLogout"
				method="post">
				<button type="submit"
					style="background-color: #333; color: #fff; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;">
					LogOut</button>
			</form>
		</div>
	</nav>

	<div class="contact-form-container">
		<div class="contact-form">
			<h2>Contact Us</h2>
			<form action="<%=request.getContextPath()%>/SubmitContactFormServlet" method="post">
				<div class="form-group">
					<label for="name">Name</label> <input type="text" id="name"
						name="name" required>
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="email" id="email"
						name="email" required>
				</div>
				<div class="form-group">
					<label for="subject">Subject</label> <input type="text"
						id="subject" name="subject" required>
				</div>
				<div class="form-group">
					<label for="message">Message</label>
					<textarea id="message" name="message" rows="5" required></textarea>
				</div>
				<button type="submit">Send Message</button>
			</form>
		</div>
	</div>
</body>
</html>
