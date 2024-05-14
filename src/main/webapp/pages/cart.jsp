<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Model.CartItemModel"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Cart</title>
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
    integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet"
    href="<%=request.getContextPath()%>/stylesheet/cart.css">
</head>
<% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") != null && session.getAttribute("user_mail")==null){
		response.sendRedirect(request.getContextPath()+"/pages/dashboard.jsp");
	}

%>
<body>
    <!-- Navbar -->
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
            <a href="${pageContext.request.contextPath}/pages/profile.jsp"><i
                class="fa-solid fa-user"></i></a> <a
                href="${pageContext.request.contextPath}/CartServlet"><i
                class="fa-solid fa-cart-shopping"></i></a>
        </div>
        
        <form action="${pageContext.request.contextPath}/UserLogout" method = "post" >
        <button type = "submit">LogOut</button>
        </form>
    </nav>

    <!-- Cart items -->
    <div class="cart">
        <h1>Your Cart</h1>
        <form action="<%=request.getContextPath()%>/ClearCartServlet"
            method="post">
            <button type="submit" class="clear_btn">
                Clear Cart<i class="fa-sharp fa-solid fa-trash"></i>
            </button>
        </form>

        <%
        List<CartItemModel> cartItems = (List<CartItemModel>) request.getAttribute("cartItems");
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItemModel item : cartItems) {
        %>
        <div class="add_carts">
            <div class="image">
                <img
                    src="<%=request.getContextPath()%>/resources/images/<%=item.getProductImage()%>"
                    alt="<%=item.getProductName()%>">
                <div class="prod_name">
                    <span><%=item.getProductName()%></span> <span>$<%=item.getProductPrice()%></span>
                </div>
            </div>

            <div class="quantity">
                <span>Quantity</span>
                <div class="Q_btn">
                    <button class="plus">+</button>
                    <p><%=item.getQuantity()%></p>
                    <button class="minus">-</button>
                </div>
            </div>
            <div class="total_amt">
                <span>Total amount</span>
                <p>
                    $<%=item.getProductPrice() * item.getQuantity()%></p>
            </div>
            <!-- Delete button for each cart item -->
            <form action="<%=request.getContextPath()%>/ManageCartServlet"
                method="post">
                <input type="hidden" name="deleteId" value="<%=item.getCartId()%>">
                <button type="submit" class="delete_btn">Remove Item</button>
            </form>
            <!-- Place order button for the entire cart -->
            <form action="<%=request.getContextPath()%>/OrderServlet"
                method="post">
                <input type="hidden" name="cartId" value="<%=item.getCartId()%>">
                <input type="hidden" name="productId" value="<%=item.getProductId()%>">
                <input type="hidden" name="quantity" class="quantity-input" value="<%=item.getQuantity()%>">
                <button type="submit" class="order_btn">Place Order</button>
            </form>
        </div>
        <%
            }
        } else {
        %>
        <p>Your cart is empty.</p>
        <%
        }
        %>
    </div>

    <script>
        document.querySelectorAll('.Q_btn button').forEach(button => {
            button.addEventListener('click', function () {
                const parentDiv = this.closest('.add_carts');
                const quantityElement = parentDiv.querySelector('.Q_btn p');
                let quantity = parseInt(quantityElement.textContent);

                if (this.classList.contains('plus')) {
                    quantity++;
                } else if (this.classList.contains('minus') && quantity > 1) {
                    quantity--;
                }

                quantityElement.textContent = quantity;

                // Update the hidden input for quantity in the same cart item container
                const hiddenQuantityInput = parentDiv.querySelector('.quantity-input');
                hiddenQuantityInput.value = quantity; // This ensures the updated quantity is sent when the form is submitted
            });
        });
    </script>
</body>
</html>
