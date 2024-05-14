package Model;

public class MessageModel {
    private int userId;
    private String name;
    private String email;
    private String subject;
    private String message;

    public MessageModel(int userId, String name, String email, String subject, String message) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    
    public MessageModel() {
    	
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}