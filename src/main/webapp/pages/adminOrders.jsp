<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.OrderModel"%>

<!DOCTYPE html>
<html>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/addProduct.css"> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
<head>
<% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") == null && session.getAttribute("user_mail") != null){
		response.sendRedirect(request.getContextPath()+"/manageDisplayServlet");
	}

%>
    <meta charset="UTF-8">
    <title>Admin Orders</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/adminOrders.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #343a40;
            margin: 0;
            padding: 0;
        }
        h1 {
            text-align: center;
            color: #343a40;
            margin: 20px 0;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
        }
        table th, table td {
            padding: 12px;
            text-align: left;
            border: 1px solid #dee2e6;
        }
        table th {
            background-color: #343a40;
            color: #ffffff;
        }
        table tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        table tr:hover {
            background-color: #e9ecef;
        }
        .complete-btn {
            background-color: #28a745;
            color: #ffffff;
            border: none;
            padding: 8px 16px;
            cursor: pointer;
            border-radius: 4px;
        }
        .complete-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<nav>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="">
        </div>
        <div class="nav-items">
            <ul>
                <li><a href="${pageContext.request.contextPath}/pages/dashboard.jsp">Dashboard</a></li>
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

    <h1>All Orders</h1>
    <table>
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Product ID</th>
                <th>User ID</th>
                <th>Quantity</th>
                <th>Status</th>
                <th>Order Date</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Shipping Address</th>
                <th>Phone Number</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<OrderModel> orders = (List<OrderModel>) request.getAttribute("orders");
                if (orders != null && !orders.isEmpty()) {
                    for (OrderModel order : orders) {
            %>
            <tr>
                <td><%= order.getOrderId() %></td>
                <td><%= order.getProductId() %></td>
                <td><%= order.getUserId() %></td>
                <td><%= order.getQuantity() %></td>
                <td><%= order.getOrderStatus() %></td>
                <td><%= order.getOrderDate() %></td>
                <td><%= order.getProductName() %></td>
                <td><%= order.getProductPrice() %></td>
                <td><%= order.getShippingAddress() %></td>
                <td><%= order.getPhoneNumber() %></td>
                <td>
                    <form action="<%=request.getContextPath()%>/CompleteOrderServlet" method="post">
                        <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                        <button type="submit" class="complete-btn">Complete</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="11">No orders found.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
