package lv.javaguru.java2.servlet;

/**
 * Created by Viesturs on 11/2/2015.
 */

import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.PropertyDAOImpl;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;






public class SearchController extends HttpServlet {
    private static PropertyDAO propertyDao = new PropertyDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();



    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String search=request.getParameter("searchProperty");
        if(search!=null && search.equals("Search")) {
            String postType = request.getParameter("postType");
            String lowerPrice = request.getParameter("lowerPrice");
            String upperPrice = request.getParameter("upperPrice");
            String adress = request.getParameter("adress");
            String lowerLivingArea = request.getParameter("lowerLivingArea");
            String upperLivingArea = request.getParameter("upperLivingArea");
            String lowerCountOfBedrooms = request.getParameter("lowerCountOfBedrooms");
            String upperCountOfBedrooms = request.getParameter("upperCountOfBedrooms");
            String lowerLandArea = request.getParameter("lowerLandArea");
            String upperLandArea = request.getParameter("upperLandArea");





            RequestDispatcher view = request.getRequestDispatcher("/searchResults.jsp");
            view.forward(request, response);

        }

    }
}




