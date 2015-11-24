package lv.javaguru.java2.servlet.mvc;


import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;



@Controller
public class NewPostRegisterController implements MVCController {

    @Autowired
    private CategoryDAO categoryDao;

    @Autowired
    private UtilityDAO propertyUtilityDao;

    @Autowired
    private PropertyDAO propertyDao;

    @Autowired
    private PropertyOwnerDAO propertyOwnerDao;

    @Autowired
    private JunctionDAO junctionDao;

    public MVCModel execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");




        if(userName!=null&&userPassword!=null){

        if(request.getParameter("start")!=null && request.getParameter("start").equals("start")){
            return new MVCModel("Login data", "/newAddRegister.jsp");
        }

            if(request.getParameter("addProperty")!=null&&request.getParameter("addProperty").equals("Next page")){
                session.setAttribute("category", request.getParameter("postType"));
                session.setAttribute("description", request.getParameter("description"));
                session.setAttribute("price", request.getParameter("price"));
                session.setAttribute("adress", request.getParameter("adress"));
                session.setAttribute("livingArea", request.getParameter("livingArea"));
                session.setAttribute("countOfBedrooms", request.getParameter("countOfBedrooms"));
                session.setAttribute("landArea", request.getParameter("landArea"));



                return new MVCModel("Login data", "/newOwnersRegister.jsp");
            }

            if(request.getParameter("addOwner")!=null && request.getParameter("addOwner").equals("Next page")){
                session.setAttribute("ownerFirstName", request.getParameter("firstName"));
                session.setAttribute("ownerLastName", request.getParameter("lastName"));
                session.setAttribute("ownerEmail", request.getParameter("email"));
                session.setAttribute("ownerPhone", request.getParameter("phone"));


                return new MVCModel("Login data", "/newUtilityRegister.jsp");
            }

            if(request.getParameter("addUtility")!=null && request.getParameter("addUtility").equals("Next page")){
                Long i=new Long(0);
                Long cg=new Long(0);
                Long ch=new Long(0);
                Long cw=new Long(0);
                Long cs=new Long(0);
                Long checked= new Long(1);


                if(request.getParameter("internet")!=null){i=checked;}

                if(request.getParameter("city_gas")!=null) {cg=checked;}

                if(request.getParameter("city_heat")!=null){ch=checked; }

                if(request.getParameter("city_water")!=null){cw=checked; }

                if(request.getParameter("city_sewer")!=null){cs=checked; }


                List<Long> utilities=new ArrayList<>();

                utilities.add(i);
                utilities.add(cg);
                utilities.add(ch);
                utilities.add(cw);
                utilities.add(cs);
/* - from this point further data are saved into database - */


                Property property=new Property();
                property.setClient((User)session.getAttribute("user"));
                CategoryName categoryName = CategoryName.valueOf((String)session.getAttribute("category"));
                Category category=new Category();

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

                try {
                    propertyUtils=propertyUtilityDao.createUtility(utilities);


                    property.setPropertyUtilities(propertyUtils);//list of utilities is set to this property;
                }
                catch (DBException e) {
                    System.out.println("Error!");
                }
/*********************************************************************************************************/

                Long lastPropertyID=null;
                try {
                    lastPropertyID = propertyDao.create(property);//save property into database and get an ID of saved property;
                    session.removeAttribute("lastPropertyID");
                    session.setAttribute("lastPropertyID", lastPropertyID);
                }catch (DBException e) {
                    System.out.println("Error!");
                }

                // returns a last inserted Id in properties;
                //insert a list of property owners into database;
                List<PropertyOwner> propertyOwnersMain = property.getPropertyOwners();
                List<Long> propertyOwnersID = new ArrayList<>();
                Long lastPropertyOwnerID = null;

                for (PropertyOwner propertyOwner : propertyOwnersMain) {

                    try{
                        lastPropertyOwnerID = propertyOwnerDao.create(propertyOwner);
                        propertyOwnersID.add(lastPropertyOwnerID);}
                    catch (DBException e) {
                        System.out.println("Error!");
                    }

                }

                try {
                    junctionDao.propertyOwnerJunction(lastPropertyID, propertyOwnersID);
                }catch (DBException e) {
                    System.out.println("Error!");
                }
                //insert a list of property utilities into junction table;

                List<Long> propertyUtilitiesId = new ArrayList<>();

                List<Utility> propertyUtilities = property.getPropertyUtilities();


                for (Utility utils : propertyUtilities) {
                    propertyUtilitiesId.add(utils.getUtilityId());

                }
                try{
                    junctionDao.propertyUtilitiesJunction(lastPropertyID, propertyUtilitiesId);}
                catch (DBException e) {
                    System.out.println("Error!");
                }
                /******************************************************************************************************/

                String postSuccessfull="Your post successfully registered in our database! Wait calls from clients!";
                session.setAttribute("postSuccessfull", postSuccessfull);


                return new MVCModel("Login data", "/imageUploadPage2.jsp");

            }







            return new MVCModel("Login data", "/clientLoggedInFirstPage1.jsp");
        }


        return new MVCModel("Login data", "/index.jsp");
    }
}




