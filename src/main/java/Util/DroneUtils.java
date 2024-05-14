package Util;

import java.io.File;

public class DroneUtils {
    
    // SQL queries
    public static final String CREATE_USER = "INSERT INTO users "
            + "(userName, email, address, password, phoneNumber, role) VALUES ( ?, ?, ?, ?, ?,?)";
    public static final String FETCH_USER = "SELECT * FROM users WHERE email = ?";
    public static final String FETCH_NAME_USER = "SELECT * FROM users WHERE userName = ?";
    public static final String FETCH_USERNAME = "SELECT COUNT(*) FROM users WHERE userName = ?";
    public static final String FETCH_USER_DETAILS = "SELECT * FROM users";
    public static final String FETCH_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
    public static final String FETCH_PHONENUMBER = "SELECT COUNT(*) FROM users WHERE phoneNumber = ?";
    public static final String CREATE_PRODUCT = "INSERT INTO products " +
            "(productName, productDescription, productPrice, productImage, productAvailability, productBrand, batteryLife, maxRange, cameraQuality, weight, controlRange, flightTime, hasGPS, videoResolution, controllerCompatibility) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
    public static final String GET_PROFILE_DETAILS = "SELECT * FROM users WHERE userId = ?";
    public static final String FETCH_SINGLE_PRODUCT = "SELECT * FROM products WHERE productId = ?";
    public static final String ADD_TO_CART = "INSERT INTO cart (userId, productId, quantity) VALUES (?, ?, ?)\r\n"
    		+ "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity);\r\n"
    		+ "";
    public static final String UPDATE_CART ="UPDATE cart SET quantity = ? WHERE userId = ? AND productId = ?";
    public static final String DELETE_SINGLE_CART = "DELETE FROM cart WHERE userId = ? AND productId = ?";
    public static final String DELETE_WHOLE_CART = "DELETE FROM cart WHERE userId = ? ";
    public static final String GET_ALL_MESSAGE = "SELECT * FROM messages";

    
    // Parameters Name from input
    public static final String USER_NAME = "userName";
    public static final String EMAIL = "email"; 
    public static final String ADDRESS =  "address"; 
    public static final String PASSWORD = "password";  
    public static final String CONFIRM_PASSWORD = "confirmPassword"; 
    public static final String PHONE_NUMBER = "phoneNumber"; 
    public static final String PRODUCT_NAME = "productName"; 
    public static final String PRODUCT_DESCRIPTION = "productDescription"; 
    public static final String PRODUCT_IMAGE = "productImage"; 
    public static final String PRODUCT_PRICE = "productPrice"; 
    public static final String PRODUCT_AVAILABILITY = "productAvailability"; 
    public static final String PRODUCT_BRAND = "productBrand"; 
    
    
    
   
    
    //Messages
    
    public static final String REGISTRATION_SUCCESS = "User has been registered successfully";
    public static final String FIX_INPUT= "Please input valid details.";
    public static final String SERVER_ERROR= "An unexpected server error occurred.";
    public static final String USERNAME_ERROR= "Username has been taken already";
    public static final String EMAIL_ERROR= "Email already Registered !! Try another email";
    public static final String PHONE_NUMBER_ALREADY_REGISTERED = "Phone Number is already registered.";
    public static final String NOT_SAME_PASSWORD = "Please Input same password for confirmation.";
    
    //
    public static final String SUCCESS_MESSAGE = "request Success";
    public static final String ERROR_MESSAGE = "User Registration Failed";
    

    public static final String IMAGE_DIR = "C:\\Users\\anir2\\eclipse-workspace\\PropelZone\\src\\main\\webapp\\resources\\images";
 
    
    //For Login
    
    public static final String LOGIN_SUCCESS = "You have been Login successfully";
    public static final String LOGIN_ERROR= "Email or Password do not match"; 
    
    public static final String REGISTER_PAGE = "/pages/signup.jsp";
    public static final String LOGIN_PAGE = "/pages/login.jsp";
    public static final String MAIN_PAGE = "/pages/home.jsp";
    public static final String LANDING_PAGE = "/pages/landing.jsp";
    public static final String CART_PAGE = "/pages/cart.jsp";
    
    
    
    public static final String REGISTER_SERVLET = "/RegisterServlet";
    public static final String LOGIN = "/LoginServlet";
    public static final String ADD_PRODUCT = "/AddProductServlet";
    public static final String FETCH_PRODUCTS_SERVLET = "/FetchProductsServlet";
    public static final String FETCH_PROFILE_SERVLET = "/FetchProfileServlet";
    public static final String SINGLE_PRODUCT = "/SingleProductServlet";
    public static final String CART_SERVLET = "/CartServlet";
    public static final String ORDER_SERVLET = "/OrderServlet";
    public static final String ADMIN_PRODUCTS_SERVLET = "/AdminProductsServlet";
    public static final String MANAGE_CART_SERVLET = "/ManageCartServlet";
    public static final String UPDATE_PROFILE = "/UpdateProfileServlet";
    
    
    public static final String ADMIN_LOGIN_SUCCESS = "Admin ligin succesfull";
    public static final String  ADMIN_DASHBOARD_PAGE ="/pages/dashboard.jsp";
}