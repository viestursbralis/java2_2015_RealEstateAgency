package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.UtilityDAO;
import lv.javaguru.java2.domain.CategoryName;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
/*public class SearchForSpecificPropertyController implements TransactionalController {

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    private UtilityDAO utilityDao;

    public MVCModel execute(HttpServletRequest request) {


        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");


        User user=new User();
        try {
            user = userDao.findUserByCredentials(userName, userPassword);

            if(user!=null){
                List<Property>clientProperties= propertyDao.findPropertyByClient(user);
                user.setListOfProperties(clientProperties);
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

        if (request.getMethod() == "POST") {
            List<String> searchCriteria = new ArrayList<>();
            String postType = request.getParameter("postType");
            searchCriteria.add(postType);
            CategoryName categoryName;
            switch (postType){
                case "CONDO_FOR_RENT": categoryName=CategoryName.CONDO_FOR_RENT; break;
                case "CONDO_FOR_SALE": categoryName=CategoryName.CONDO_FOR_SALE; break;
                case "CONDO_TO_RENT": categoryName=CategoryName.CONDO_TO_RENT; break;
                case "CONDO_TO_BUY": categoryName=CategoryName.CONDO_TO_BUY; break;
                case "HOUSE_FOR_RENT": categoryName=CategoryName.HOUSE_FOR_RENT; break;
                case "HOUSE_FOR_SALE": categoryName=CategoryName.HOUSE_FOR_SALE; break;
                case "HOUSE_TO_RENT": categoryName=CategoryName.HOUSE_TO_RENT; break;
                case "HOUSE_TO_BUY": categoryName=CategoryName.HOUSE_TO_BUY; break;
                default: String category = "Not provided";
            }


            String lowerPrice = request.getParameter("lowerPrice");
            searchCriteria.add(lowerPrice);
            double lowerPriceDouble = Double.parseDouble(lowerPrice);
            String upperPrice = request.getParameter("upperPrice");
            searchCriteria.add(upperPrice);
            double upperPriceDouble = Double.parseDouble(upperPrice);
            String adress = request.getParameter("adress");
            searchCriteria.add(adress);
            String lowerLivingArea = request.getParameter("lowerLivingArea");
            searchCriteria.add(lowerLivingArea);
            Long lowerLivingAreaLong = Long.valueOf(lowerLivingArea);
            String upperLivingArea = request.getParameter("upperLivingArea");
            searchCriteria.add(upperLivingArea);
            Long upperLivingAreaLong = Long.valueOf(upperLivingArea);
            String lowerCountOfBedrooms = request.getParameter("lowerCountOfBedrooms");
            searchCriteria.add(lowerCountOfBedrooms);
            int lowerCountOfBedroomsInt = Integer.valueOf(lowerCountOfBedrooms);
            String upperCountOfBedrooms = request.getParameter("upperCountOfBedrooms");
            searchCriteria.add(upperCountOfBedrooms);
            int upperCountOfBedroomsInt = Integer.valueOf(upperCountOfBedrooms);
            String lowerLandArea = request.getParameter("lowerLandArea");
            searchCriteria.add(lowerLandArea);
            Long lowerLandAreaLong = Long.valueOf(lowerLandArea);
            String upperLandArea = request.getParameter("upperLandArea");
            searchCriteria.add(upperLandArea);
            Long upperLandAreaLong = Long.valueOf(upperLandArea);

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
            try {
                List<Utility> utils = utilityDao.createUtility(utilities);
            } catch (DBException e) {
                System.out.println("Error!");
            }

        }





        return new MVCModel("Login data", "/searchForSpecificProperty.jsp");
    }

}*/
@Transactional
public class SearchForSpecificPropertyController {

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("JDBC_UtilityDAO")
    private UtilityDAO utilityDao;

    @RequestMapping(value="search", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse repsonse) {


        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");


        User user=new User();
        try {
            user = userDao.findUserByCredentials(userName, userPassword);

            if(user!=null){
                List<Property>clientProperties= propertyDao.findPropertyByClient(user);
                user.setListOfProperties(clientProperties);
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

        if (request.getMethod() == "POST") {
            List<String> searchCriteria = new ArrayList<>();
            String postType = request.getParameter("postType");
            searchCriteria.add(postType);
            CategoryName categoryName;
            switch (postType){
                case "CONDO_FOR_RENT": categoryName=CategoryName.CONDO_FOR_RENT; break;
                case "CONDO_FOR_SALE": categoryName=CategoryName.CONDO_FOR_SALE; break;
                case "CONDO_TO_RENT": categoryName=CategoryName.CONDO_TO_RENT; break;
                case "CONDO_TO_BUY": categoryName=CategoryName.CONDO_TO_BUY; break;
                case "HOUSE_FOR_RENT": categoryName=CategoryName.HOUSE_FOR_RENT; break;
                case "HOUSE_FOR_SALE": categoryName=CategoryName.HOUSE_FOR_SALE; break;
                case "HOUSE_TO_RENT": categoryName=CategoryName.HOUSE_TO_RENT; break;
                case "HOUSE_TO_BUY": categoryName=CategoryName.HOUSE_TO_BUY; break;
                default: String category = "Not provided";
            }


            String lowerPrice = request.getParameter("lowerPrice");
            searchCriteria.add(lowerPrice);
            double lowerPriceDouble = Double.parseDouble(lowerPrice);
            String upperPrice = request.getParameter("upperPrice");
            searchCriteria.add(upperPrice);
            double upperPriceDouble = Double.parseDouble(upperPrice);
            String adress = request.getParameter("adress");
            searchCriteria.add(adress);
            String lowerLivingArea = request.getParameter("lowerLivingArea");
            searchCriteria.add(lowerLivingArea);
            Long lowerLivingAreaLong = Long.valueOf(lowerLivingArea);
            String upperLivingArea = request.getParameter("upperLivingArea");
            searchCriteria.add(upperLivingArea);
            Long upperLivingAreaLong = Long.valueOf(upperLivingArea);
            String lowerCountOfBedrooms = request.getParameter("lowerCountOfBedrooms");
            searchCriteria.add(lowerCountOfBedrooms);
            int lowerCountOfBedroomsInt = Integer.valueOf(lowerCountOfBedrooms);
            String upperCountOfBedrooms = request.getParameter("upperCountOfBedrooms");
            searchCriteria.add(upperCountOfBedrooms);
            int upperCountOfBedroomsInt = Integer.valueOf(upperCountOfBedrooms);
            String lowerLandArea = request.getParameter("lowerLandArea");
            searchCriteria.add(lowerLandArea);
            Long lowerLandAreaLong = Long.valueOf(lowerLandArea);
            String upperLandArea = request.getParameter("upperLandArea");
            searchCriteria.add(upperLandArea);
            Long upperLandAreaLong = Long.valueOf(upperLandArea);

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
            try {
                List<Utility> utils = utilityDao.createUtility(utilities);
            } catch (DBException e) {
                System.out.println("Error!");
            }

        }





        return new ModelAndView("searchForSpecificProperty", "model", null);
    }

}