<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.ProductModel"%>

<!DOCTYPE html>
<html lang="en">
<head>
<% 
    request.getSession(false);
    if(session.getAttribute("admin_mail") != null && session.getAttribute("user_mail")==null){
        response.sendRedirect(request.getContextPath()+"/pages/dashboard.jsp");
    }
%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet/home.css">
    <style>
       
        .details h4, .details p {
            color: black; /* Ensures text is black */
            text-decoration: none; /* Removes any text decoration */
            font-size: 16px; /* Sets a standard font size for all text */
        }
        .details h4 {
            font-weight: bold; /* Adds boldness to the product name */
        }
        .message-container, .search-container, .collections {
            text-align: center;
            background: white;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            border-radius: 8px;
            margin-top: 20px;
        }
        a, a:hover {
            text-decoration: none; /* Removes text decoration from links */
            color: black; /* Ensures link text is black */
        }
        .cart-btn {
            background-color: black;
            color: white;
            padding: 5px 20px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            outline: none;
            border:none;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
        }
    </style>
</head>

<body>
    <nav>
        <div class="logo">
            <img src="<%=request.getContextPath()%>/images/whitelogo.png" alt="Logo">
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

    <section class="video-container">
        <div class="overlay"></div>
        <video autoplay loop muted>
            <source src="<%=request.getContextPath()%>/images/bg-video.mp4" type="video/mp4">
        </video>
        <h1>Fly smarter, <br> fly farther</h1>
    </section>

    <h1 style="text-align: center; margin-top: 80px; font-family: Arial, sans-serif; color: #333;">Our Collections</h1>

    <!-- Search bar and button -->
    <div class="search-container" style="text-align: center; margin: 20px;">
        <form action="${pageContext.request.contextPath}/SearchServlet" method="get">
            <input type="text" placeholder="Search products..." name="search" style="padding: 8px; width: 20%; min-width: 200px;">
            <button type="submit" style="padding: 10px 20px; background-color: #000; color: white; border: none; cursor: pointer;">
                <i class="fa fa-search"></i> Search
            </button>
        </form>
    </div>

    <section class="collections">
    <% List<ProductModel> products = (List<ProductModel>) request.getAttribute("products");
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
    <%   }
       } else {
    %>
    <p>No products found.</p>
    <%   }
    %>
    </section>

    <footer>
        <div class="footer-left">
            <img src="<%=request.getContextPath()%>/images/whitelogo.png" alt="Logo">
            <p>Reach new heights!</p>
        </div>
        <div class="footer-right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/contact">Contact</a></li>
                <li><a href="<%=request.getContextPath()%>/collections">Collections</a></li>
                <li><a href="<%=request.getContextPath()%>/about">About us</a></li>
            </ul>
        </div>
        <div class="icons">
            <i class="fa-brands fa-facebook"></i>
            <i class="fa-brands fa-instagram"></i>
            <i class="fa-brands fa-skype"></i>
            <i class="fa-brands fa-twitter"></i>
        </div>
    </footer>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
        const nav = document.querySelector('nav');
        const logo = document.querySelector('.logo img');
        const navItems = document.querySelectorAll('.nav-items a');
        const shortcuts = document.querySelectorAll('.shortcuts i');

        window.addEventListener('scroll', function() {
            if (window.pageYOffset > 0) {
                nav.style.backgroundColor = 'white';
                navItems.forEach(item => item.style.color = 'black');
                shortcuts.forEach(icon => icon.style.color = 'black');
                logo.src = '<%=request.getContextPath()%>/images/logo.png';
            } else {
                nav.style.backgroundColor = 'transparent';
                navItems.forEach(item => item.style.color = 'white');
                shortcuts.forEach(icon => icon.style.color = 'white');
                logo.src = '<%=request.getContextPath()%>/images/whitelogo.png';
            }
        });
    });
    </script>

</body>
</html>
	