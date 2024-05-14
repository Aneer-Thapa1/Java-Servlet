package Controller.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Database.DatabaseController;
import Model.UserModel;
import Util.DroneUtils;

@WebServlet(asyncSupported = true, urlPatterns = { DroneUtils.REGISTER_SERVLET })
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DatabaseController dbController = new DatabaseController();

    public RegisterServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printOut = response.getWriter();
        String userName = request.getParameter(DroneUtils.USER_NAME);
        String email = request.getParameter(DroneUtils.EMAIL);
        String address = request.getParameter(DroneUtils.ADDRESS);
        String password = request.getParameter(DroneUtils.PASSWORD);
        String confirmPassword = request.getParameter(DroneUtils.CONFIRM_PASSWORD);
        String phoneNumber = request.getParameter(DroneUtils.PHONE_NUMBER);
        String role = "user";
        
      
        UserModel userModel = new UserModel(userName, email, address, password, phoneNumber,role);
        int pushObj = dbController.addUser(userModel);

        if (password.equals(confirmPassword)) {
         switch (pushObj) {
                case 1:
                    request.setAttribute(DroneUtils.SUCCESS_MESSAGE, DroneUtils.REGISTRATION_SUCCESS);
              request.getRequestDispatcher(DroneUtils.LOGIN_PAGE).forward(request, response);
                    break;

                case 0:
                    request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.ERROR_MESSAGE);
                    request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
                    break;

                case -1:
                    request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.SERVER_ERROR);
                    request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
                    break;

                case -2:
                    request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.USERNAME_ERROR);
                    request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
                    break;

                case -3:
                    request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.EMAIL_ERROR);
                    request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
                    break;

                case -4:
                    request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.PHONE_NUMBER_ALREADY_REGISTERED);
                    request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
                    break;

                default:
                    request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.NOT_SAME_PASSWORD);
                    request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
                    break;
            }
        } else {
            request.setAttribute(DroneUtils.ERROR_MESSAGE, DroneUtils.NOT_SAME_PASSWORD);
            request.getRequestDispatcher(DroneUtils.REGISTER_PAGE).forward(request, response);
        }
    }
}