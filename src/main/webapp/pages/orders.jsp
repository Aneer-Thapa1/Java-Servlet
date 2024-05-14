<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <% 
    request.getSession(false);
	if(session.getAttribute("admin_mail") == null && session.getAttribute("user_mail") != null){
		response.sendRedirect(request.getContextPath()+"/manageDisplayServlet");
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>