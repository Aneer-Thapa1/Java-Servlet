<%@page import="Util.DroneUtils"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheet/login.css">

</head>
<body>

<main>
        <div class="box">
            <div class="inner-box">
                <div class="forms-wrap">
                  <form action="${pageContext.request.contextPath}/LoginServlet" autocomplete="off" class="sign-in-form" method="post">
                        <div class="logo">
                            <img src="${pageContext.request.contextPath}/images/drone3.png" alt="easyclass" />
                            <h4>propel zone</h4>
                        </div>

                        <div class="heading-signin">
                            <h2>Welcome Back</h2>
                            <div>
                            </div>
                        </div>

                        <div class="actual-form">
                            <div class="input-wrap">
                                <input type="email" class="input-field" autocomplete="off" required name="email" />
                                <label>Email</label>
                            </div>

                            <div class="input-wrap">
                                <input type="password" class="input-field" autocomplete="off" required
                                    name="password" />
                                <label>Password</label>
                            </div>
                            
                                    <% 
        String successMessage = (String) request.getAttribute(DroneUtils.SUCCESS_MESSAGE);
        String errorMessage = (String) request.getAttribute(DroneUtils.ERROR_MESSAGE);

        if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
        <!-- Display error message -->
         <div class="alert alert-danger mt-2" role="alert">
            <%= errorMessage %>
        </div>
        <% } %>

        <% 
        if (successMessage != null && !successMessage.isEmpty( )) {
        %>
        <!-- Display success message -->
        <div class="alert alert-success mt-2" role="alert">
            <%= successMessage %>
        </div>
        <% } %>

                            <input type="submit" value="Sign In" class="sign-btn" />

                            <div class="text">
                                <h6>Not registred yet?</h6>
                                <a href="${pageContext.request.contextPath}/pages/signup.jsp">Sign up</a>
                            </div>

                        </div>
                    </form>


                </div>

                <div class="carousel">
                    <div class="images-wrapper">
                     
                        <img src="${pageContext.request.contextPath}/images/drone5.png" class="image img-2 show" alt="" />
                        <img src="${pageContext.request.contextPath}/images/droneimg.png" class="image img-3" alt="" />
                    </div>

                    <div class="text-slider">
                        <div class="text-wrap">
                            <div class="text-group">
                                <h2>Elevate Your Experience</h2>
                                <h2>Reach New Heights!</h2>
                                <h2>Explore Our Drones!</h2>
                            </div>
                        </div>

                        <div class="bullets">
                            <span class="active" data-value="1"></span>
                            <span data-value="2"></span>
                            <span data-value="3"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>



<script>
const inputs = document.querySelectorAll(".input-field");

const main = document.querySelector("main");
const bullets = document.querySelectorAll(".bullets span");
const images = document.querySelectorAll(".image");

inputs.forEach((inp) => {
  inp.addEventListener("focus", () => {
    inp.classList.add("active");
  });
  inp.addEventListener("blur", () => {
    if (inp.value != "") return;
    inp.classList.remove("active");
  });
});

function moveSlider() {
  let index = this.dataset.value;

  let currentImage = document.querySelector(`.img-${index}`);
  images.forEach((img) => img.classList.remove("show"));
  currentImage.classList.add("show");

  const textSlider = document.querySelector(".text-group");
  textSlider.style.transform = `translateY(${-(index - 1) * 2.2}rem)`;

  bullets.forEach((bull) => bull.classList.remove("active"));
  this.classList.add("active");
}

bullets.forEach((bullet) => {
  bullet.addEventListener("click", moveSlider);
});


</script>

</body>
</html>