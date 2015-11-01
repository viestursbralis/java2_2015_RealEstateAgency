package lv.javaguru.java2.servlet;

/**
 * Created by Viesturs on 10/30/2015.
 */

import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.*;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;

import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.http.*;
        import java.io.*;
        import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeerSelect extends HttpServlet {
    private static PropertyDAO propertyDao = new PropertyDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();



    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException{

        //String userName = request.getParameter("username");
        //String userPassword = request.getParameter("password");
        String userName = "kaamis@gmail.com";
        String userPassword = "kaamis";

        User user=new User();
        try {
            user = userDao.findUserByCredentials(userName, userPassword);
        }        catch (DBException e) {
            System.out.println("Error!");
        }
        //setListOfUserPropertiesMain(user);
        try {
            List<Property> propertiesByThisUser = propertyDao.findPropertyByClient(user);
            user.setListOfProperties(propertiesByThisUser);
        }catch (DBException e) {
            System.out.println("Error!");
        }


        System.out.println("This is current (logged in) user:");
        System.out.println(user);

        String loggedInUserFirstName=user.getFirstName();
        String loggedInUserLastName = user.getLastName();
        List<Property>result =user.getListOfProperties();


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        out.println("<h1>" + "Logged in users first name:" + loggedInUserFirstName +"</h1>");
        out.println("<h1>" + "Logged in users last name:" + loggedInUserLastName +"</h1>");
        out.println("List of properties <br>");

        for(Property prop:result){
            out.println("<3h>" + prop +" <h3>");
        }
    }
}

