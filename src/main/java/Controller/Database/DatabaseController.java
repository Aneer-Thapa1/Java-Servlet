package Controller.Database;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.CartItemModel;

import Model.Hashing;
import Model.MessageModel;
import Model.OrderModel;
import Model.UserModel;
import Model.ProductModel;
import Util.DroneUtils;

public class DatabaseController {

	// Establishes a connection to the database
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/propel_zone";
		String user = "root";
		String pass = "anir2080";
		return DriverManager.getConnection(url, user, pass);
	}

	public int getUserIdByEmail(String email) throws SQLException, ClassNotFoundException {
		int userId = -1; // Default value for userId
		String userAddress = "";
		try (Connection con = getConnection()) {

			PreparedStatement statement = con.prepareStatement(DroneUtils.FETCH_USER);
			statement.setString(1, email);
		

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					userId = resultSet.getInt("userId");
					userAddress = resultSet.getString("address");
					System.out.println("Retrieved userId: " + userId); // Print the userId for debugging
				} else {
					System.out.println("No user found with email: " + email); // Print when no user is found
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error retrieving user by email: " + e.getMessage()); // Print error message
			throw e; // Rethrow the exception to handle it upstream
		}

		return userId;
	}

    public int addUser(UserModel userModel) {
        try (Connection con = getConnection()) {
            String hashPassword = Hashing.encryptPassword(userModel.getPassword(), "U3CdwubLD5yQbUOG92ZnHw==");

            // Prepare statement for inserting user
            try (PreparedStatement insertUserStatement = con.prepareStatement(DroneUtils.CREATE_USER)) {
                insertUserStatement.setString(1, userModel.getUserName());
                insertUserStatement.setString(2, userModel.getEmail());
                insertUserStatement.setString(3, userModel.getAddress());
                insertUserStatement.setString(4, hashPassword);
                insertUserStatement.setString(5, userModel.getPhoneNumber());
                insertUserStatement.setString(6, userModel.getRole());
                // Set default image path
                

                // Prepare statement for verifying username uniqueness
                try (PreparedStatement verifyUsernameStatement = con.prepareStatement(DroneUtils.FETCH_USERNAME)) {
                    verifyUsernameStatement.setString(1, userModel.getUserName());
                    try (ResultSet verifyUsernameResultSet = verifyUsernameStatement.executeQuery()) {
                        verifyUsernameResultSet.next();
                        if (verifyUsernameResultSet.getInt(1) > 0) {
                            return -2; // Username already exists
                        }
                    }
                }

                // Prepare statement for verifying if email is unique or not
                try (PreparedStatement verifyEmailStatement = con.prepareStatement(DroneUtils.FETCH_EMAIL)) {
                    verifyEmailStatement.setString(1, userModel.getEmail());
                    try (ResultSet verifyEmailResultSet = verifyEmailStatement.executeQuery()) {
                        verifyEmailResultSet.next();
                        if (verifyEmailResultSet.getInt(1) > 0) {
                            return -3; // Email already exists
                        }
                    }
                }

                // Prepare statement for verifying if phone number is unique or not
                try (PreparedStatement verifyPhoneStatement = con.prepareStatement(DroneUtils.FETCH_PHONENUMBER)) {
                    verifyPhoneStatement.setString(1, userModel.getPhoneNumber());
                    try (ResultSet verifyPhoneResultSet = verifyPhoneStatement.executeQuery()) {
                        verifyPhoneResultSet.next();
                        if (verifyPhoneResultSet.getInt(1) > 0) {
                            return -4; // Phone Number already exists
                        }
                    }
                }

                // Execute insert query
                int createQuery = insertUserStatement.executeUpdate();
                return createQuery > 0 ? 1 : 0; // if user registered
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

	public int userLogin(String email, String password) {
		try (Connection con = getConnection()) {
			PreparedStatement fetchUserStatement = con.prepareStatement(DroneUtils.FETCH_USER);
			fetchUserStatement.setString(1, email);

			try (ResultSet resultSet = fetchUserStatement.executeQuery()) {
				if (resultSet.next()) {
					String getEmail = resultSet.getString("email"); // datbase ko email leko
					String gethashedPassword = resultSet.getString("password"); // database ko password leko
					String getRole = resultSet.getString("role");
					int getUserId = resultSet.getInt("userId");

					String unHashPassword = Hashing.decryptPassword(gethashedPassword, "U3CdwubLD5yQbUOG92ZnHw==");

					if (unHashPassword != null && getEmail.equals(email) && unHashPassword.equals(password)
							&& getRole.equals("Admin")) {
						return 7; // Admin login successful
					}

					else if (unHashPassword != null && getEmail.equals(email) && unHashPassword.equals(password)) {
						return 1; // Login successful
					} else {
						return 0; // Incorrect password
					}
				} else {
					return 0; // No match for this record
				}
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public UserModel fetchUserDetails(String email) throws ClassNotFoundException {
		try (Connection con = getConnection();
				PreparedStatement userstatement = con.prepareStatement(DroneUtils.FETCH_USER)) {
			userstatement.setString(1, email);

			try (ResultSet resultSet = userstatement.executeQuery()) {
				if (resultSet.next()) {
					UserModel user = new UserModel();
					user.setUserName(resultSet.getString("userName"));
					user.setEmail(resultSet.getString("email"));
					user.setAddress(resultSet.getString("address"));
					user.setPhoneNumber(resultSet.getString("phoneNumber"));
					return user;
				} else {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean isValidProduct(ProductModel productModel) {
		return productModel != null && productModel.getProductName() != null && !productModel.getProductName().isEmpty()
				&& productModel.getProductDescription() != null && !productModel.getProductDescription().isEmpty()
				&& productModel.getProductPrice() > 0 && productModel.getProductImage() != null
				&& !productModel.getProductImage().isEmpty() && productModel.getProductAvailability() != null
				&& !productModel.getProductAvailability().isEmpty() && productModel.getProductBrand() != null
				&& !productModel.getProductBrand().isEmpty() && productModel.getBatteryLife() > 0
				&& productModel.getMaxRange() > 0 && productModel.getCameraQuality() != null
				&& !productModel.getCameraQuality().isEmpty() && productModel.getWeight() > 0
				&& productModel.getControlRange() > 0 && productModel.getFlightTime() > 0
				&& productModel.getVideoResolution() != null && !productModel.getVideoResolution().isEmpty()
				&& productModel.getControllerCompatibility() != null
				&& !productModel.getControllerCompatibility().isEmpty();
	}

	public int addProduct(ProductModel productModel) {
		try (Connection con = getConnection();
				PreparedStatement insertProductStatement = con.prepareStatement(DroneUtils.CREATE_PRODUCT)) {

			insertProductStatement.setString(1, productModel.getProductName());
			insertProductStatement.setString(2, productModel.getProductDescription());
			insertProductStatement.setDouble(3, productModel.getProductPrice());
			insertProductStatement.setString(4, productModel.getProductImage());
			insertProductStatement.setString(5, productModel.getProductAvailability());
			insertProductStatement.setString(6, productModel.getProductBrand());
			insertProductStatement.setInt(7, productModel.getBatteryLife());
			insertProductStatement.setDouble(8, productModel.getMaxRange());
			insertProductStatement.setString(9, productModel.getCameraQuality());
			insertProductStatement.setDouble(10, productModel.getWeight());
			insertProductStatement.setDouble(11, productModel.getControlRange());
			insertProductStatement.setInt(12, productModel.getFlightTime());
			insertProductStatement.setBoolean(13, productModel.isHasGPS());
			insertProductStatement.setString(14, productModel.getVideoResolution());
			insertProductStatement.setString(15, productModel.getControllerCompatibility());

			// Execute insert query
			int affectedRows = insertProductStatement.executeUpdate();
			System.out.println("Kati number aaeraxa" + affectedRows);
			return affectedRows > 0 ? 1 : 0; 
			
	// If the query is successfully executed
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return -1; // Indicates an error occurred during the operation
		}
	}

	public List<ProductModel> getAllProducts() {
		List<ProductModel> products = new ArrayList<>();
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(DroneUtils.GET_ALL_PRODUCTS);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ProductModel product = new ProductModel();
				 product.setProductId(rs.getInt("productId"));
		            product.setProductName(rs.getString("productName"));
		            product.setProductDescription(rs.getString("productDescription"));
		            product.setProductPrice(rs.getInt("productPrice"));
		            product.setProductImage(rs.getString("productImage"));
		            product.setProductBrand(rs.getString("productBrand"));
		            product.setProductAvailability(rs.getString("productAvailability"));
		            product.setBatteryLife(rs.getInt("batteryLife"));
		            product.setMaxRange(rs.getDouble("maxRange"));
		            product.setCameraQuality(rs.getString("cameraQuality"));
		            product.setWeight(rs.getDouble("weight"));
		            product.setControlRange(rs.getDouble("controlRange"));
		            product.setFlightTime(rs.getInt("flightTime"));
		            product.setHasGPS(rs.getBoolean("hasGPS"));
		            product.setVideoResolution(rs.getString("videoResolution"));
		            product.setControllerCompatibility(rs.getString("controllerCompatibility"));
				products.add(product);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
		}
		return products;
	}

	public ProductModel getProductById(int productId) {
		ProductModel product = null;
		// Adjust table and column names as necessary

		try (Connection con = getConnection();
				PreparedStatement stmt = con.prepareStatement(DroneUtils.FETCH_SINGLE_PRODUCT)) {

			stmt.setInt(1, productId); // Set the ID parameter for the query
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				product = new ProductModel();
				
				product.setProductId(rs.getInt("productId"));
				product.setProductName(rs.getString("productName"));
				product.setProductDescription(rs.getString("productDescription"));
				product.setProductPrice(rs.getInt("productPrice"));
				product.setProductImage(rs.getString("productImage"));
				product.setProductBrand(rs.getString("productBrand"));
				product.setProductAvailability(rs.getString("productAvailability"));
				product.setBatteryLife(rs.getInt("batteryLife"));
				product.setMaxRange(rs.getDouble("maxRange"));
				product.setCameraQuality(rs.getString("cameraQuality"));
				product.setWeight(rs.getDouble("weight"));
				product.setControlRange(rs.getDouble("controlRange"));
				product.setFlightTime(rs.getInt("flightTime"));
				product.setHasGPS(rs.getBoolean("hasGPS"));
				product.setVideoResolution(rs.getString("videoResolution"));
				product.setControllerCompatibility(rs.getString("controllerCompatibility"));
				
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("SQL error when fetching product by ID: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Driver not found");
		}
		return product;
	}

	// Adds an item to the cart or updates the quantity if the item already exists
	public int addToCart(int userId, int productId, int quantity) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();

			// Check if the product already exists in the user's cart
			String checkQuery = "SELECT count(*) FROM cart WHERE userId = ? AND productId = ?";
			pstmt = con.prepareStatement(checkQuery);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, productId);
			rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				return 1; // Product already exists
			}

			String insertQuery = "INSERT INTO cart (userId, productId, quantity) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, productId);
			pstmt.setInt(3, quantity);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows == 0) {
				return -1; // Failed to add the product, no rows affected
			}

			return 0; // Successfully added
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return -1; // Indicates an error occurred during the operation
	}

	// Removes an item from the cart
	public boolean removeFromCart(int cartId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM cart WHERE cartId = ?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, cartId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0; // Return true if at least one row is affected (item removed)
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e; // Rethrow to allow higher-level handlers to manage the error
		}
	}

	// Clears all items from a user's cart
	public void clearCart(int userId) throws Exception {
		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(DroneUtils.DELETE_WHOLE_CART)) {
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e; // Rethrow to allow higher-level handlers to manage the error
		}
	}

	public List<CartItemModel> getCartItems(int userId) throws Exception {
		List<CartItemModel> cartItems = new ArrayList<>();
		String sql = "SELECT c.cartId, c.userId, c.productId, c.quantity, p.productName, p.productImage, p.productPrice FROM cart c INNER JOIN products p ON c.productId = p.productId WHERE c.userId = ?";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			System.out.println("Executing query with userId: " + userId); // Log the user ID being queried
			ResultSet rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				System.out.println("No data found for user: " + userId); // No rows returned
			}
			while (rs.next()) {
				CartItemModel item = new CartItemModel(rs.getInt("cartId"), rs.getInt("userId"), rs.getInt("productId"),
						rs.getInt("quantity"), rs.getString("productName"), rs.getString("productImage"),
						rs.getInt("productPrice"));
				cartItems.add(item);
				System.out.println("Added item: " + item.getProductName() + " with quantity: " + item.getQuantity()); // Log
																														// details
																														// of
																														// each
																														// item
																														// added
			}
			rs.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace(); // Print stack trace
			throw e; // Rethrow to allow higher-level handlers to manage the error
		}
		System.out.println("Total items retrieved: " + cartItems.size()); // Log total items retrieved
		return cartItems;
	}

	// Method to place an order in the database
	public boolean placeOrder(int userId, String orderStatus, int productId, int quantity) {
		
	
		String sql = "INSERT INTO orders (userId, orderStatus, productId, quantity, orderDate) VALUES (?, ?, ?, ?, NOW())";
		try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			pstmt.setString(2, orderStatus);
			pstmt.setInt(3, productId);
			pstmt.setInt(4, quantity);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0; // Return true if rows were affected
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false; // Return false if an exception occurs
		}
	}
	
	
	public List<OrderModel> fetchOrders(int userId) throws ClassNotFoundException {
	    List<OrderModel> orders = new ArrayList<>();

	    // SQL joins orders table with users and products table to fetch address, product name, and product price
	    String sql = "SELECT o.orderId, o.userId, o.productId, o.quantity, o.orderStatus, o.orderDate, u.address AS userAddress, " +
	            "p.productName, p.productPrice " +
	            "FROM orders o " +
	            "JOIN users u ON o.userId = u.userId " +
	            "JOIN products p ON o.productId = p.productId " +
	            "WHERE o.userId = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        con = getConnection(); // Get connection from your connection method
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, userId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            OrderModel order = new OrderModel();
	            order.setOrderId(rs.getInt("orderId"));
	            order.setUserId(rs.getInt("userId"));
	            order.setProductId(rs.getInt("productId"));
	            order.setQuantity(rs.getInt("quantity"));
	            order.setOrderStatus(rs.getString("orderStatus"));
	            order.setOrderDate(rs.getString("orderDate"));
	            order.setShippingAddress(rs.getString("userAddress"));
	            order.setProductName(rs.getString("productName")); 
	            order.setProductPrice(rs.getInt("productPrice")); 
	            orders.add(order);
	        }

	    } catch (SQLException e) {
	        System.out.println("Database access error occurred: " + e.getMessage());
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            System.out.println("Error closing database resources: " + e.getMessage());
	        }
	    }

	    return orders;
	}
	
	
	  // Combined method to search products by name or price
    public List<ProductModel> searchProducts(String searchQuery) throws Exception {
        List<ProductModel> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // Using the single connection method
            if (searchQuery.matches("-?\\d+(\\.\\d+)?")) { // Regex to check if input is numeric
                double price = Double.parseDouble(searchQuery);
                String sql = "SELECT * FROM products WHERE productPrice >= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, price);
            } else {
                String sql = "SELECT * FROM products WHERE productName LIKE ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + searchQuery + "%");
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("productId"));
				product.setProductName(rs.getString("productName"));
				product.setProductDescription(rs.getString("productDescription"));
				product.setProductPrice(rs.getInt("productPrice"));
				product.setProductImage(rs.getString("productImage"));
				product.setProductBrand(rs.getString("productBrand"));
				product.setProductAvailability(rs.getString("productAvailability")); // Corrected to getDouble if your DB stores prices as doubles
                products.add(product);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return products;
    }


    

    public boolean updateUserData(UserModel user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET userName = ?, address = ?, email = ?, phoneNumber = ?, role = ? WHERE userId = ?";

        try (Connection conn = getConnection();  // Assuming `getConnection()` is defined elsewhere in your DatabaseController.
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the user details in the PreparedStatement.
            pstmt.setString(1, user.getUserName()); // Set userName
            pstmt.setString(2, user.getAddress());  // Set address
            pstmt.setString(3, user.getEmail());     // Set email
            pstmt.setString(4, user.getPhoneNumber()); // Set phoneNumber
            pstmt.setString(5, user.getRole());         // Set role
            pstmt.setInt(6, user.getUserId());          // Set userId at the correct index

            int updatedRows = pstmt.executeUpdate(); // Execute the update statement
            if (updatedRows > 0) {
                System.out.println("Update Successful: " + updatedRows + " rows updated.");
                return true;
            } else {
                System.out.println("Query Execution Failed: No rows updated. Query: " + pstmt.toString());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            throw e;
        }
    }


    
    
    public boolean updateProduct(int productId, ProductModel product) throws ClassNotFoundException {
        String sql ="UPDATE products SET productName = ?, productDescription = ?, productImage = ?, productPrice = ?, productAvailability = ?, " +
        		"productBrand = ?, batteryLife = ?, maxRange = ?, cameraQuality = ?, weight = ?, controlRange = ?, " +
        		"flightTime = ?, hasGPS = ?, videoResolution = ?, controllerCompatibility = ? WHERE productId = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductDescription());
            
            // Assuming productImage is a URL or file path after saving to disk
            stmt.setString(3, product.getProductImage());
            
            stmt.setInt(4, product.getProductPrice());
            stmt.setString(5, product.getProductAvailability());
            stmt.setString(6, product.getProductBrand());
            stmt.setInt(7, product.getBatteryLife());
            stmt.setDouble(8, product.getMaxRange());
            stmt.setString(9, product.getCameraQuality());
            stmt.setDouble(10, product.getWeight());
            stmt.setDouble(11, product.getControlRange());
            stmt.setInt(12, product.getFlightTime());
            stmt.setBoolean(13, product.isHasGPS());
            stmt.setString(14, product.getVideoResolution());
            stmt.setString(15, product.getControllerCompatibility());
            stmt.setInt(16, productId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider a more robust logging or error handling mechanism
            return false;
        }
    }
    
    public boolean deleteProduct(int productId) throws ClassNotFoundException, Exception {
        String sql = "DELETE FROM products WHERE productId = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);  // Set product ID to the prepared statement

            int rowsAffected = stmt.executeUpdate();  // Execute the DELETE statement
            return rowsAffected > 0;  // Return true if the product was deleted successfully
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception (ideally to a logger, not to System.out)
            return false;  // Return false if there was an error during deletion
        }
    }
    
    
    public List<OrderModel> fetchAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.orderId, o.productId, o.userId, o.quantity, o.orderStatus, o.orderDate, " +
                "p.productName, p.productPrice, u.address, u.phoneNumber " +
                "FROM orders o " +
                "JOIN products p ON o.productId = p.productId " +
                "JOIN users u ON o.userId = u.userId";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                int productId = resultSet.getInt("productId");
                int userId = resultSet.getInt("userId");
                int quantity = resultSet.getInt("quantity");
                String orderStatus = resultSet.getString("orderStatus");
                String orderDate = resultSet.getString("orderDate");
                String productName = resultSet.getString("productName");
                String address = resultSet.getString("address");
                int productPrice = resultSet.getInt("productPrice");
                String phoneNumber = resultSet.getString("phoneNumber");

                OrderModel order = new OrderModel(productId, userId, quantity, orderStatus, orderDate);
                order.setOrderId(orderId);
                order.setProductName(productName);
                order.setProductPrice(productPrice);
                order.setShippingAddress(address);
                order.setPhoneNumber(phoneNumber);
                
                orders.add(order);
            }
        }
        return orders;
    }
    
    public void markOrderAsCompleted(int orderId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE orders SET orderStatus = 'completed' WHERE orderId = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }
    
    
    public int insertMessage(MessageModel messageModel) throws SQLException {
        
        String query = "INSERT INTO messages (userId, name, email, subject, message) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, messageModel.getUserId());
            preparedStatement.setString(2, messageModel.getName());
            preparedStatement.setString(3, messageModel.getEmail());
            preparedStatement.setString(4, messageModel.getSubject());
            preparedStatement.setString(5, messageModel.getMessage());

            int affectedRows = preparedStatement.executeUpdate();
			System.out.println("Kati number aaeraxa" + affectedRows);
			return affectedRows > 0 ? 1 : 0; 
			
	// If the query is successfully executed
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return -1; // Indicates an error occurred during the operation
		}
    }
    
    public List<MessageModel> getAllMessages() {
        List<MessageModel> messages = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(DroneUtils.GET_ALL_MESSAGE);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                MessageModel message = new MessageModel();
                message.setUserId(rs.getInt("userId"));
                message.setMessage(rs.getString("message"));
                message.setEmail(rs.getString("email"));
                message.setName(rs.getString("name"));
                message.setSubject(rs.getString("subject"));
                messages.add(message);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception for debugging
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
        }
        return messages;
    }
}
