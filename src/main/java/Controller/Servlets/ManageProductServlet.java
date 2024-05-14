package Controller.Servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Util.DroneUtils;
import Model.ProductModel;
import Controller.Database.DatabaseController;

@WebServlet(asyncSupported = true, urlPatterns = { "/ManageProductServlet" })
public class ManageProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseController dbController;

	public ManageProductServlet() {
		super();
		// Initialize the database controller here, which handles all database
		// operations
		this.dbController = new DatabaseController();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String updateIdStr = request.getParameter("productId");
		String deleteIdStr = request.getParameter("deleteId");

		
		try {
			if (deleteIdStr != null && !deleteIdStr.isEmpty()) {
				int deleteId = Integer.parseInt(deleteIdStr);
				doDelete(request, response, deleteId);
			} else {
				  request.getRequestDispatcher("/pages/message.jsp").forward(request, response); // Redirect to an error page if no valid action is found
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response, int productId)
			throws ServletException, IOException {
		try {
			dbController.deleteProduct(productId);
			  request.getRequestDispatcher("/DisplayMessageServlet").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting product");
		}
	}
}