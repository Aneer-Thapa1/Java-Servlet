<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="Model.UserModel"%>

<%
if (session.getAttribute("admin_mail") != null && session.getAttribute("user_mail") == null) {
    response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/stylesheet/profile.css">
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <%--   <%@ include file="navbar.jsp" %> --%>
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
            <a href="${pageContext.request.contextPath}/pages/profile.jsp" class="active"><i
                class="fa-solid fa-user"></i></a>
            <a href="${pageContext.request.contextPath}/CartServlet"><i
                class="fa-solid fa-cart-shopping"></i></a>
            <form action="${pageContext.request.contextPath}/UserLogout"
                method="post">
                <button type="submit"
                    style="background-color: black; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;">
                    LogOut</button>
            </form>
        </div>
    </nav>

    <%
    UserModel user = (UserModel) session.getAttribute("profileDetails");
    if (user != null) {
    %>

    <div class="profile">
        <div class="p_side">
            <img
                src="https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png">
            <div class="detail">
                <p>
                    Name:
                    <%=user.getUserName()%></p>
                <p>
                    Email:<%=user.getEmail()%></p>
                <p>
                    Phone:
                    <%=user.getPhoneNumber()%></p>
                <p>
                    Address:
                    <%=user.getAddress()%>
                </p>
            </div>
            <div class="edit">
                <i id="pen" class="fa-sharp fa-solid fa-pen"></i>
            </div>
            <!-- Add this code after the closing </div> of the .edit class -->

        </div>


        <%
        } else {
        // Handle the case where user is null
        }
        %>
    </div>

    <div id="overlay" class="overlay"></div>
    <div id="popup" class="popup">
        <div class="popup-content">
            <span class="close-btn">&times;</span>
            <h2>Edit Profile</h2>
            <form
                action="${pageContext.request.contextPath}/UpdateProfileServlet"
                method="post" >
                <input type="text" name="userName" placeholder="User Name"
                    value="<%=user.getUserName()%>"> <input type="email"
                    name="email" placeholder="email" value="<%=user.getEmail()%>">
                <input type="text" name="address" placeholder="Address"
                    value="<%=user.getAddress()%>"> <input type="text"
                    name="phoneNumber" placeholder="Phone Number"
                    value="<%=user.getPhoneNumber()%>">
                <button type="submit">Save Changes</button>
            </form>
        </div>
    </div>

    <script>
        const editBtn = document.getElementById('pen');
        const overlay = document.getElementById('overlay');
        const popup = document.getElementById('popup');
        const closeBtn = document.querySelector('.close-btn');

        editBtn.addEventListener('click', () => {
            overlay.style.display = 'block';
            popup.style.display = 'block';
        });

        closeBtn.addEventListener('click', () => {
            overlay.style.display = 'none';
            popup.style.display = 'none';
        });

        window.addEventListener('click', (e) => {
            if (e.target === overlay) {
                overlay.style.display = 'none';
                popup.style.display = 'none';
            }
        });
    </script>
</body>
</html>
