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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



public class AgencyClientsChoiceRouter extends HttpServlet {
    private static PropertyDAO propertyDao = new PropertyDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();



    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String clientChoice = request.getParameter("clientsChoiceA");

        response.setContentType("text/html");

        // Prepare output html
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Value choosed by client is:" + "</h1>");
        out.println("<h1>" + clientChoice + "</h1>");


if(clientChoice.equals("1")){
    RequestDispatcher view = request.getRequestDispatcher("/newAddRegister.jsp");
    view.forward(request, response);

}



    }
}


