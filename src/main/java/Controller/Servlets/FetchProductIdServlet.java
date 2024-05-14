package Controller.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Database.DatabaseController;
import Model.ProductModel;

/**
 * Servlet implementation class GetProductServlet
 */
@WebServlet("/FetchProductIdServlet")
public class FetchProductIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FetchProductIdServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int selectedProductId = Integer.parseInt(request.getParameter("productId"));
		System.out.println(selectedProductId);
		System.out.println("Selected Product Name: " + selectedProductId);

		if (selectedProductId != 0) {
			DatabaseController dbController = new DatabaseController();
			ProductModel product = dbController.getProductById(selectedProductId);
			if (product != null) {
				request.setAttribute("product", product);
				request.getRequestDispatcher("/pages/editProduct.jsp").forward(request, response);
			} else {
				response.getWriter().println("Product not found.");
			}
		} else {
			response.getWriter().println("No product selected.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}