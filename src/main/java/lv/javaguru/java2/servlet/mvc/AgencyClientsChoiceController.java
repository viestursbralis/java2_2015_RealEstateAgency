package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
public class AgencyClientsChoiceController implements TransactionalController {
    @Autowired @Qualifier("ORM_PropertyDAO")
    PropertyDAO propertyDao;


    public MVCModel execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String clientChoice = request.getParameter("clientsChoiceA");
        if(clientChoice.equals("1")){
            return new MVCModel("Some data", "/newAddRegister.jsp");
         }


        if(clientChoice.equals("2")){

            List<Property> allProperties = new ArrayList<Property>();

            try {
                allProperties = propertyDao.findAllProperties();
            }catch (DBException e) {
                System.out.println("Error!");
            }


            return new MVCModel(allProperties, "/listAllProperties.jsp");
        }

        if(clientChoice.equals("3")){
            return new MVCModel("Some data", "/searchForSpecificProperty.jsp");
        }


        if(clientChoice.equals("4")) {
            User user=(User)session.getAttribute("user");

            List<Property> propertiesOfThisUser = user.getListOfProperties();
          return new MVCModel(propertiesOfThisUser, "/deleteSpecificProperty.jsp");

        }

        if(clientChoice.equals("5")) {

            session.invalidate();
            return new MVCModel("", "/index.jsp");

        }


            return new MVCModel("Some data", "/index.jsp");
    }

}
