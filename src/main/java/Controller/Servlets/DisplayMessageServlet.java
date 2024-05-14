package Controller.Servlets;

import Controller.Database.DatabaseController;
import Model.MessageModel;
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
   urlPatterns = {"/DisplayMessageServlet"}
)
public class DisplayMessageServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private DatabaseController dbController = new DatabaseController();

   public DisplayMessageServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      List<MessageModel> messages = this.dbController.getAllMessages();
      System.out.println(messages);
      request.setAttribute("messages", messages);
      request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      List<MessageModel> messages = this.dbController.getAllMessages();
      System.out.println(messages);
      req.setAttribute("messages", messages);
      req.getRequestDispatcher("/pages/dashboard.jsp").forward(req, resp);
   }
}