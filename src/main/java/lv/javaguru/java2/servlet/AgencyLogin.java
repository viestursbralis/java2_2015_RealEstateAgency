package lv.javaguru.java2.servlet;

/**
 * Created by Viesturs on 10/30/2015.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.PropertyDAOImpl;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



public class AgencyLogin extends HttpServlet {
    private static PropertyDAO propertyDao = new PropertyDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();



    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");


        User user=new User();
        try {
            user = userDao.findUserByCredentials(userName, userPassword);

            if(user!=null){
                String userFirstName=user.getFirstName();
                String userLastName=user.getLastName();
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userFirstName", userFirstName);
                session.setAttribute("userLastName", userLastName);
                session.setAttribute("userName", userName);
                session.setAttribute("userPassword", userPassword);
                session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
                session.setAttribute("agentLastName", user.getAgent().getAgentLastName());
            }
        }        catch (DBException e) {
            System.out.println("Error!");
        }


        RequestDispatcher view = request.getRequestDispatcher("/clientLoggedInFirstPage.jsp");
        view.forward(request, response);


    }
}


