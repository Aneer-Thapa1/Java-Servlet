	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") == null && session.getAttribute("user_mail") != null){
		response.sendRedirect(request.getContextPath()+"/manageDisplayServlet");
	}

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/orderConfirmation.css"> 
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
    
    <div class="content">
        <h1>Order Confirmation</h1>
        <p><%= request.getAttribute("message") %></p> <!-- Displaying the message from servlet -->
        <a href="<%=request.getContextPath()%>/home">Return to Home</a>
    </div>
</body>
</html>
