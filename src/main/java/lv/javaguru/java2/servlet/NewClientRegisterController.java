package lv.javaguru.java2.servlet;

/**
 * Created by Viesturs on 10/30/2015.
 */

import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.AgentDAOImpl;
import lv.javaguru.java2.database.jdbc.PropertyDAOImpl;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.Statuss;
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



public class NewClientRegisterController extends HttpServlet {
    private static PropertyDAO propertyDao = new PropertyDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();
private static AgentDAO agentDao = new AgentDAOImpl();


    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher view = request.getRequestDispatcher("/newClientRegister.jsp");
        view.forward(request, response);

    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String userFirstName = request.getParameter("firstName");
        String userLastName = request.getParameter("lastName");
        String userEmail = request.getParameter("email");
        String userPassword = request.getParameter("password");



            User newUser= new User();
        Agent lessBusyAgent = new Agent();
        try {
            lessBusyAgent = agentDao.findLessBusyAgent();
        }catch (DBException e) {
            System.out.println("Error!");
        }

            newUser.setFirstName(userFirstName);
            newUser.setLastName(userLastName);
            newUser.setUserEmail(userEmail);
            newUser.setPassword(userPassword);
            newUser.setStatuss(Statuss.CLIENT);
        newUser.setAgent(lessBusyAgent);
            try {
                userDao.create(newUser);


                HttpSession session = request.getSession();
                session.setAttribute("userFirstName", userFirstName);
                session.setAttribute("userLastName", userLastName);

                RequestDispatcher view = request.getRequestDispatcher("/clientLoggedInFirstPage.jsp");
                view.forward(request, response);

            } catch (DBException e) {
                System.out.println("Error!");
            }








    }




}


