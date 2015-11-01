package lv.javaguru.java2.servlet;

/**
 * Created by Viesturs on 10/30/2015.
 */

import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.*;
import lv.javaguru.java2.domain.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;



public class NewPostRegisterController extends HttpServlet {




    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher view = request.getRequestDispatcher("/newClientRegister.jsp");
        view.forward(request, response);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");




if(userName!=null&&userPassword!=null){


    if(request.getParameter("addProperty")!=null&&request.getParameter("addProperty").equals("Next page")){
        session.setAttribute("category", request.getParameter("postType"));
        session.setAttribute("description", request.getParameter("description"));
        session.setAttribute("price", request.getParameter("price"));
        session.setAttribute("adress", request.getParameter("adress"));
        session.setAttribute("livingArea", request.getParameter("livingArea"));
        session.setAttribute("countOfBedrooms", request.getParameter("countOfBedrooms"));
        session.setAttribute("landArea", request.getParameter("landArea"));

        RequestDispatcher view = request.getRequestDispatcher("/newOwnersRegister.jsp");
        view.forward(request, response);
    }

    if(request.getParameter("addOwner")!=null && request.getParameter("addOwner").equals("Next page")){
        session.setAttribute("ownerFirstName", request.getParameter("firstName"));
        session.setAttribute("ownerLastName", request.getParameter("lastName"));
        session.setAttribute("ownerEmail", request.getParameter("email"));
        session.setAttribute("ownerPhone", request.getParameter("phone"));

        RequestDispatcher view = request.getRequestDispatcher("/newUtilityRegister.jsp");
        view.forward(request, response);
    }

    if(request.getParameter("addUtility")!=null && request.getParameter("addUtility").equals("Register!")){
        Long i=null;
        Long cg=null;
        Long ch=null;
        Long cw=null;
        Long cs=null;
        String internet=(String)request.getParameter("internet");

        if(internet.equals("1")){i=Long.parseLong(internet, 10);}

        String city_gas=(String)(request.getParameter("city_gas"));
        if(city_gas.equals("1")){cg=Long.parseLong(city_gas, 10); }

        String city_heat=(String)(request.getParameter("city_heat"));
        if(city_heat.equals("1")){ch=Long.parseLong(city_heat, 10);}

        String city_water=(String)(request.getParameter("city_water"));
        if(city_water.equals("1")){cw=Long.parseLong(city_water, 10);}

        String city_sewer=(String)request.getParameter("city_sewer");
        if(city_sewer.equals("1")){cs=Long.parseLong(city_sewer, 10);}


List<Long>utilities=new ArrayList<>();

        utilities.add(i);
        utilities.add(cg);
        utilities.add(ch);
        utilities.add(cw);
        utilities.add(cs);

        Property property=new Property();
        property.setClient((User)session.getAttribute("user"));
        CategoryName categoryName = CategoryName.valueOf((String)session.getAttribute("category"));
        Category category=new Category();
        CategoryDAO categoryDao = new CategoryDAOImpl();
        try {
            category = categoryDao.findCategoryByName(categoryName);
            property.setCategory(category);
        }catch (DBException e) {
            System.out.println("Error!");
        }

        property.setPropertyDescription((String)session.getAttribute("description"));
        property.setPrice((Double.parseDouble((String)session.getAttribute("price"))));
        property.setAdress((String)session.getAttribute("adress"));
        property.setArea((Long.parseLong((String)session.getAttribute("livingArea"), 10)));
        property.setCountOfBedrooms(Integer.parseInt((String)session.getAttribute("countOfBedrooms")));
        property.setLandArea((Long.parseLong((String)session.getAttribute("landArea"))));
        PropertyOwner owner = new PropertyOwner();
        List<PropertyOwner>propertyOwners=new ArrayList<>();
        owner.setFirstName((String)session.getAttribute("ownerFirstName"));
        owner.setLastName((String)session.getAttribute("ownerLastName"));
        owner.setOwnerEmail((String)session.getAttribute("ownerEmail"));
        owner.setOwnerPhone((String)session.getAttribute("ownerPhone"));
        propertyOwners.add(owner);
        property.setPropertyOwners(propertyOwners);
        List<Utility> propertyUtils =new ArrayList<>();
        UtilityDAO propertyUtilityDao=new UtilityDAOImpl();
        try {
            propertyUtils=propertyUtilityDao.createUtility(utilities);


            property.setPropertyUtilities(propertyUtils);
        }
        catch (DBException e) {
            System.out.println("Error!");
        }
/*********************************************************************************************************/
    PropertyDAO propertyDao=new PropertyDAOImpl();
        Long lastPropertyID=null;
        try {
            lastPropertyID = propertyDao.create(property);//save property into database and
        }catch (DBException e) {
            System.out.println("Error!");
        }

        // returns a last inserted Id in properties;
        //insert a list of property owners into database;
        List<PropertyOwner> propertyOwnersMain = property.getPropertyOwners();
        List<Long> propertyOwnersID = new ArrayList<>();
        Long lastPropertyOwnerID = null;
        PropertyOwnerDAO propertyOwnerDao=new PropertyOwnerDAOImpl();
        for (PropertyOwner propertyOwner : propertyOwnersMain) {

            try{
            lastPropertyOwnerID = propertyOwnerDao.create(propertyOwner);
            propertyOwnersID.add(lastPropertyOwnerID);}
            catch (DBException e) {
                System.out.println("Error!");
            }

        }
        JunctionDAO junctionDao=new JunctionDAOImpl();
        try {
            junctionDao.propertyOwnerJunction(lastPropertyID, propertyOwnersID);
        }catch (DBException e) {
            System.out.println("Error!");
        }
        //insert a list of property utilities into junction table;

        List<Long> propertyUtilitiesId = new ArrayList<>();

        List<Utility> propertyUtilities = new ArrayList<>();
        propertyUtilities = property.getPropertyUtilities();
        for (Utility utils : propertyUtilities) {
            propertyUtilitiesId.add(utils.getUtilityId());

        }
        try{
        junctionDao.propertyUtilitiesJunction(lastPropertyID, propertyUtilitiesId);}
        catch (DBException e) {
            System.out.println("Error!");
        }
    /******************************************************************************************************/

        String postSuccessfull="Your post successfully registered to our database! Wait calls from clients!";
        session.setAttribute("postSuccessfull", postSuccessfull);
        RequestDispatcher view = request.getRequestDispatcher("/clientLoggedInFirstPage.jsp");
        view.forward(request, response);
    }



}





    }




}


