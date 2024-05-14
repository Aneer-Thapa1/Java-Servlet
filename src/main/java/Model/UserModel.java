package Model;

import javax.servlet.http.Part;

import Util.DroneUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UserModel {
	private int userId;
    private String userName;
    private String email;
    private String address;
    private String password;
    private String phoneNumber;
    private String role;
    private String userImage; // Path to the user's profile image

    public UserModel(String userName, String email, String address, String password, String phoneNumber, String role) {
        super();
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        
    }

    public UserModel() {
        // Default constructor
    }

 

	

	// Method to save image to disk and return the filename
    private String saveImageToDisk(Part imagePart) {
        if (imagePart != null) {
            Path directory = Paths.get(DroneUtils.IMAGE_DIR); // Specify the directory
            try {
                if (Files.notExists(directory)) {
                    Files.createDirectories(directory);
                }
                String fileName = UUID.randomUUID().toString() + "_" + extractFileName(imagePart);
                Path filePath = directory.resolve(fileName);

                Files.copy(imagePart.getInputStream(), filePath); // Save the image
                return fileName; // Return the stored file name
            } catch (IOException e) {
                e.printStackTrace();
                return "default.png"; // Fallback image path
            }
        }
        return null; // Return null if no image was provided
    }

    // Helper method to extract the filename from a Part object
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 2, content.length() - 1);
            }
        }
        return "";
    }

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
