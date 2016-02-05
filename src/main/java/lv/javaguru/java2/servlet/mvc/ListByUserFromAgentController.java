package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 14-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




/*public class ListByUserFromAgentController  implements TransactionalController{//Ajax controller;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;


    @RequestMapping(value="listAllPropertiesOfThisUserFromAgent", method={RequestMethod.GET})
    public  MVCModel execute(HttpServletRequest request) {


        String idToSee0 = request.getParameter("ID");//ID of user which property list we want to see;
         Long idToSee = Long.parseLong(String.valueOf(idToSee0));


        User user = new User();
        List<Property>propertiesOfThisUser=new ArrayList<>();
        try {
            user = userDao.getUserById(idToSee);
            if (user != null) {
                propertiesOfThisUser = propertyDao.findPropertyByClient(user);
                user.setListOfProperties(propertiesOfThisUser);

            }
        }  catch (DBException e) {
            System.out.println("Error!");
        }

        return new MVCModel (propertiesOfThisUser, "listUsersByAgents.jsp");

    }

}*/
@Component
@RestController
@Transactional
@EnableWebMvc
public class ListByUserFromAgentController {//Ajax controller;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;


    @RequestMapping(value="/lAPOTUFA/{ID}", method={RequestMethod.GET}, headers="Accept=*/*", produces = "application/json")

public @ResponseBody List<Property> execute(@PathVariable(value="ID") Long ID ){


        User user = new User();
        List<Property>propertiesOfThisUser=new ArrayList<>();
        List<Property>propertiesToShow = new ArrayList<>();
        Property p = new Property();

        try {
            user = userDao.getUserById(ID);
            if (user != null) {
                propertiesOfThisUser = propertyDao.findPropertyByClient(user);
                user.setListOfProperties(propertiesOfThisUser);
for(Property prop:propertiesOfThisUser){
    Property property = new Property();
    property.setPropertyDescription(prop.getPropertyDescription());
    property.setPrice(prop.getPrice());
    property.setArea(prop.getArea());
    property.setLandArea(prop.getLandArea());
    propertiesToShow.add(property);
}



            }

        }  catch (DBException e) {
            System.out.println("Error!");
        }
p.setPropertyId(Long.valueOf(1));
        p.setPrice(200);
        //return propertiesOfThisUser;
        return propertiesToShow;

    }

}

