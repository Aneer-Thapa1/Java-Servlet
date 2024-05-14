// Source code is decompiled from a .class file using FernFlower decompiler.
package Controller.Filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({"/*"})
public class AuthenticationFilter implements Filter {
   public AuthenticationFilter() {
   }

   public void destroy() {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest)request;
      HttpServletResponse res = (HttpServletResponse)response;
      String uri = req.getRequestURI();
      if (uri.endsWith(".css")) {
         chain.doFilter(request, response);
      } else if (!uri.endsWith("login.jsp") && !uri.endsWith("landing.jsp") && !uri.endsWith("signup.jsp")) {
         if (!uri.endsWith("RegisterServlet") && !uri.endsWith("CartServlet") && !uri.endsWith("OrderServlet") && !uri.endsWith("searchResults.jsp")&&   !uri.endsWith("AddProductServlet")&&  !uri.endsWith("FetchProductServlet") && !uri.contains("SingleProductServlet") && !uri.endsWith("landing.jsp") && !uri.endsWith("/") && !uri.endsWith("CheckCookieServlet")) {
            if (!uri.endsWith(".png") && !uri.endsWith(".jpeg") && !uri.endsWith(".mp4") && !uri.endsWith(".jpg")) {
               boolean isLogin = uri.endsWith("login.jsp");
               boolean isHome = uri.endsWith("/");
               boolean isLoginServlet = uri.endsWith("LoginServlet");
               boolean isLogoutServlet = uri.endsWith("LogoutServlet");
               HttpSession session = req.getSession(false);
               boolean isLoggedIn = session != null && session.getAttribute("user_mail") != null;
               boolean isAdmin = session != null && session.getAttribute("admin_mail") != null;
               if (isAdmin) {
                  chain.doFilter(request, response);
               } else if ((isLoggedIn || !isLogin) && !isLoginServlet) {
                  if (!isLoggedIn) {
                 
                     res.sendRedirect(req.getContextPath() + "/pages/login.jsp");
                  } else if (isLoggedIn && isLogin && !isLogoutServlet) {
                     res.sendRedirect(req.getContextPath() + "/pages/home.jsp");
                  } else {
                     chain.doFilter(request, response);
                  }

               } else {
                  chain.doFilter(req, res);
               }
            } else {
               chain.doFilter(request, response);
            }
         } else {
            chain.doFilter(request, response);
         }
      } else {
         chain.doFilter(request, response);
      }
   }

   public void init(FilterConfig arg0) throws ServletException {
   }
}
