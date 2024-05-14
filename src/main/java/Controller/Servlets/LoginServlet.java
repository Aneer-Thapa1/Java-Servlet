package Controller.Servlets;

import Controller.Database.DatabaseController;
import Model.UserModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
   asyncSupported = true,
   urlPatterns = {"/LoginServlet"}
)
public class LoginServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final DatabaseController DatabaseController = new DatabaseController();

   public LoginServlet() {
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      System.out.println(email);
      int responsed = this.DatabaseController.userLogin(email, password);
      System.out.println(responsed);
      HttpSession userSession;
      Cookie userCookie;
      UserModel user;
      if (responsed == 7) {
         userSession = request.getSession();
         userSession.setAttribute("admin_mail", email);
         userSession.setMaxInactiveInterval(864000);
         userCookie = new Cookie("admin_mail", email);
         userCookie.setMaxAge(864000);
         response.addCookie(userCookie);
         user = null;

         try {
            user = this.DatabaseController.fetchUserDetails(email);
         } catch (ClassNotFoundException var11) {
            var11.printStackTrace();
         }

         System.out.println(user);
         if (user != null) {
            request.setAttribute("request Success", "Admin ligin succesfull");
            request.getRequestDispatcher("/DisplayMessageServlet").forward(request, response);
         } else {
            request.setAttribute("User Registration Failed", "Admin details not found");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
         }
      } else if (responsed == 1) {
         userSession = request.getSession();
         userSession.setAttribute("user_mail", email);
         userSession.setMaxInactiveInterval(864000);
         userCookie = new Cookie("user_mail", email);
         userCookie.setMaxAge(864000);
         response.addCookie(userCookie);
         user = null;

         try {
            user = this.DatabaseController.fetchUserDetails(email);
         } catch (ClassNotFoundException var10) {
            var10.printStackTrace();
         }

        
         if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("profileDetails", user);
            request.setAttribute("request Success", "You have been Login successfully");
            request.getRequestDispatcher("/FetchProductsServlet").forward(request, response);
         } else {
            request.setAttribute("User Registration Failed", "User does not exist");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
         }
      } else if (responsed == 0) {
         request.setAttribute("User Registration Failed", "Email or Password do not match");
         request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
      } else {
         request.setAttribute("User Registration Failed", "An unexpected server error occurred.");
         request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
      }

   }
}