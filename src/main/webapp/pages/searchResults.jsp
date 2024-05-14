<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.ProductModel"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/searchResult.css"> <!-- Update with your actual CSS file path -->
</head>
<% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") != null && session.getAttribute("user_mail")==null){
		response.sendRedirect(request.getContextPath()+"/pages/dashboard.jsp");
	}

%>
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

		</div>
		<form action="${pageContext.request.contextPath}/UserLogout" method = "post" >
        <button type = "submit">LogOut</button>
        </form>
		
	</nav>

    <h1 style="text-align: center; margin-top: 20px;">Search Results</h1>

    <section class="collections">
        <% 
        List<ProductModel> products = (List<ProductModel>) request.getAttribute("searchResults");
        if (products != null && !products.isEmpty()) {
            for (ProductModel product : products) {
        %>
        <div class="collection">
            <a href="${pageContext.request.contextPath}/pages/singleProduct.jsp?id=<%=product.getProductId()%>">
                <img src="${pageContext.request.contextPath}/resources/images/<%=product.getProductImage()%>"
                    alt="<%=product.getProductName()%>" class="product-img">
                <div class="details">
                    <h4><%=product.getProductName()%></h4>
                    <p>Price: $<%=product.getProductPrice()%></p>
                </div>
            </a>
            <form action="${pageContext.request.contextPath}/CartServlet" method="POST">
                <input type="hidden" name="productId" value="<%=product.getProductId()%>">
                <input type="hidden" name="quantity" value="1"> <!-- Assuming a default quantity of 1 -->
                <button type="submit" class="cart-btn">
                    Add to Cart <i class="fa-solid fa-cart-shopping"></i>
                </button>
            </form>
        </div>
        <% 
            }
        } else {
        %>
        <p style="text-align: center;">No products found.</p>
        <% 
        }
        %>
    </section>

    <footer>
        <!-- Footer code here (You can place your existing footer code here) -->
    </footer>
</body>
</html>
