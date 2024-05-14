<% 
	
	if(request.getAttribute("products") == null){
		response.sendRedirect(request.getContextPath()+"/FetchProductsServlet");
	}
	
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.ProductModel"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Landing Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/landing.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
</head>
<body>
<nav>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="logo" alt="">

    <ul>
        <li><a href="#home">Home</a></li>
        <li><a href="#collections">Collections</a></li>
        <li><a href="#about">About</a></li>
        <li><a href="#contact">Contact</a></li>
    </ul>

    <div class="buttons">
        <button class="signup">
            <a href="${pageContext.request.contextPath}/pages/signup.jsp">sign up</a>
        </button>
        <button class="login">
            <a href="${pageContext.request.contextPath}/pages/login.jsp">login</a>
        </button>
    </div>
</nav>

<section id="home" class="home">
    <div class="home-left">
        <h1>Reach New Heights,<br>with your own Drones!</h1>
        <a href="../pages/signup.jsp"><button class="explore-btn">Explore More <i class="fa-solid fa-arrow-right"></i></button></a>
    </div>
    <div class="home-right">
        <img src="https://i.gifer.com/SpxP.gif" class="flying-drone" alt="">
    </div>
</section>

<h1 class="title">Our top collections</h1>
<section id="collections" class="collections">
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
        <button type="submit" class="cart-btn">
            Add to Cart <i class="fa-solid fa-cart-shopping"></i>
        </button>
    </div>
    <%   }
       } else {
    %>
    <p>No products found.</p>
    <%   }
    %>
</section>

<div class="more-div">
    <p>You want to see more products??</p>
    <button class="see-more">See More</button>
</div>

<h1 class="title">About us</h1>
<section id="about" class="about">
    <div class="about-left">
        <div class="upper">
            <div class="stats">
                <h2>No of users</h2>
                <h1>3261 + </h1>
            </div>
            <div class="line"></div>
            <div class="stats">
                <h2>Drones sold</h2>
                <h1>2021 + </h1>
            </div>
            <div class="line"></div>
            <div class="stats">
                <h2>Satisfied Customers</h2>
                <h1>1908 +</h1>
            </div>
        </div>
        <div class="lower"></div>
    </div>

    <div class="about-right">
        <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3565.7835061921282!2d87.29937287513654!3d26.65541327110118!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39ef6ea070e7b18b%3A0x2959e2a3e2bf54e0!2sItahari%20International%20College!5e0!3m2!1sen!2snp!4v1712680043034!5m2!1sen!2snp"
            width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"></iframe>
    </div>
</section>

<h1 class="title">Contact us</h1>
<section id="contact" class="contact">
    <img src="${pageContext.request.contextPath}/images/contact.jpg" alt="">
    <form action="" class="contact-us">
        <p>Questions or concerns? We're here to help!</p>
        <div class="name">
            <input type="text" placeholder="First name">
            <input type="text" placeholder="Last name">
        </div>
        <input type="email" placeholder="Email">
        <textarea name="" id="" cols="30" rows="10" placeholder="Message..."></textarea>
        <button class="contact-btn">Submit</button>
    </form>
</section>

<footer>
    <div class="footer-left">
        <img src="${pageContext.request.contextPath}/images/whitelogo.png" alt="">
        <p>Reach new heights!</p>
    </div>
    <div class="footer-right">
        <div class="text">
            <ul>
                <li><a href="#home">Home</a></li>
                <li><a href="#contact">Contact</a></li>
                <li><a href="#collections">Collections</a></li>
                <li><a href="#how-to-buy">How to buy?</a></li>
                <li><a href="#why-us">Why us?</a></li>
                <li><a href=""></a></li>
            </ul>
        </div>
    </div>
    <div class="icons">
        <i class="fa-brands fa-facebook"></i>
        <i class="fa-brands fa-instagram"></i>
        <i class="fa-brands fa-skype"></i>
        <i class="fa-brands fa-x-twitter"></i>
    </div>
</footer>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const cartButtons = document.querySelectorAll(".cart-btn");
        
        cartButtons.forEach(button => {
            button.addEventListener("click", function(event) {
                event.preventDefault();
                swal({
                    title: "Login Required",
                    text: "You need to login first to add items to the cart.",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                }).then((willLogin) => {
                    if (willLogin) {
                        window.location.href = "${pageContext.request.contextPath}/pages/login.jsp";
                    }
                });
            });
        });

        const navLinks = document.querySelectorAll('nav ul li a');
        navLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                event.preventDefault();
                const targetId = this.getAttribute('href').substring(1);
                document.getElementById(targetId).scrollIntoView({
                    behavior: 'smooth'
                });
            });
        });
    });
    
</script>

</body>
</html>
