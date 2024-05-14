
package Controller.Servlets;

import Controller.Database.DatabaseController;
import Model.ProductModel;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
   asyncSupported = true,
   urlPatterns = {"/HomeServlet"}
)
public class HomeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private DatabaseController dbController = new DatabaseController();

   public HomeServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      List<ProductModel> products = this.dbController.getAllProducts();
      request.setAttribute("products", products);
      request.getRequestDispatcher("/pages/home.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      List<ProductModel> products = this.dbController.getAllProducts();
      req.setAttribute("products", products);
      req.getRequestDispatcher("/pages/home.jsp").forward(req, resp);
   }
}
