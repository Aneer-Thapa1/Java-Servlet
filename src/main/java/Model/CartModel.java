package Model;

public class CartModel {
    private int cartId;   // This will be set automatically by the database upon insertion
    private int userId;
    private int productId;
    private int quantity;

    // Default constructor
    public CartModel() {
    }

    // Constructor with all fields except cartId, which is auto-generated by the database
    public CartModel(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
