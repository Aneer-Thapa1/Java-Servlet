<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Notification</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ffffff; /* white background for the overall page */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .message-container {
            text-align: center;
            background: white; /* white background for the container */
            padding: 50px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1); /* subtle shadow for a light depth effect */
            border-radius: 8px;
            color: #333; /* darker text for better readability */
        }
        .message-container h1 {
            color: #333; /* consistent text color for headings */
            margin-bottom: 20px;
        }
        .message-container p {
            margin-bottom: 20px;
            font-size: 16px;
            color: #555; /* slightly lighter text color for paragraph */
        }
        .message-container a {
            color: #ffffff; /* white text color */
            background-color: #000000; /* black background for the button */
            padding: 12px 24px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            border: none; /* no border for a cleaner look */
            transition: background-color 0.3s, color 0.3s; /* smooth transition for hover effects */
        }
        .message-container a:hover {
            background-color: #555; /* darker grey for hover */
            color: #ffffff; /* maintain white text on hover */
        }
    </style>
</head>
<% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") != null && session.getAttribute("user_mail")==null){
		response.sendRedirect(request.getContextPath()+"/pages/dashboard.jsp");
	}

%>
<body>
    <div class="message-container">
        <h1>${messageTitle}</h1>
        <p>${message}</p>
        
        <form action="${pageContext.request.contextPath}/CartServlet" method="POST">
        
        <button type="submit"></button>
        </form>
        <a href="${pageContext.request.contextPath}/HomeServlet">Return to Home Page</a>
    </div>
</body>
</html>
