<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.MessageModel" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Product</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/addProduct.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            color: #333;
            margin: 0;
            padding: 0;
        }
        nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fff;
            padding: 10px 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        nav .logo img {
            width: 150px;
        }
        nav .nav-items ul {
            list-style: none;
            display: flex;
            gap: 20px;
        }
        nav .nav-items ul li {
            margin: 0;
        }
        nav .nav-items ul li a {
            text-decoration: none;
            
            font-weight: bold;
            transition: color 0.3s;
        }
        nav .nav-items ul li a:hover {
          
        }
        nav form button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .feedback-container {
            padding: 20px;
        }
        .feedback-header {
            text-align: center;
            margin-bottom: 20px;
        }
        .message-box {
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            position: relative;
        }
        .message-box p {
            margin: 5px 0;
        }
        .delete-button {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px;
            cursor: pointer;
        }
    </style>
    <script>
        function confirmDelete(messageId) {
            if (confirm('Are you sure you want to delete this message?')) {
                document.getElementById('deleteForm-' + messageId).submit();
            }
        }
    </script>
</head>
<body>
<%
    if(session.getAttribute("admin_mail") == null && session.getAttribute("user_mail") != null){
        response.sendRedirect(request.getContextPath()+"/pages/home.jsp");
    }
%>
<nav>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
    </div>
    <div class="nav-items">
        <ul>
            <li><a href="${pageContext.request.contextPath}/DisplayMessageServlet">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/addProduct.jsp">Add Product</a></li>
            <li><a href="${pageContext.request.contextPath}/AdminProductsServlet">View Products</a></li>
            <li><a href="${pageContext.request.contextPath}/AdminOrderServlet">View Orders</a></li>
        </ul>
    </div>
    <form action="${pageContext.request.contextPath}/UserLogout" method="post">
        <button type="submit">
            LogOut
        </button>
    </form>
</nav>

<div class="feedback-container">
    <h2 class="feedback-header">Feedback and Messages</h2>
    <% List<MessageModel> messages = (List<MessageModel>) request.getAttribute("messages");
       if (messages != null && !messages.isEmpty()) {
           for (MessageModel message : messages) {
    %>
    <div class="message-box">
        <p><strong>UserName:</strong> <%= message.getName() %></p>
        <p><strong>Email:</strong> <%= message.getEmail() %></p>
        <p><strong>Subject:</strong> <%= message.getSubject() %></p>
        <p><strong>Message:</strong> <%= message.getMessage() %></p>
        <form id="deleteForm" method="post" action="<%=request.getContextPath()%>/DeleteMessageServlet" style="display: inline;">
            <input type="hidden" name="messageId" value="">
            <button type="button" class="delete-button" >Delete</button>
        </form>
    </div>
    <%   }
       } else {
    %>
    <p>No Messages found.</p>
    <%   }
    %>
</div>

</body>
</html>
