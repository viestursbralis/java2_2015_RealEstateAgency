package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 27-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.ServiceDAO;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;


@Component
@RestController
@Transactional
@EnableWebMvc
public class SeeWaitingListForThisAgentController {//Ajax controller;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_ServiceDAO")
    private ServiceDAO serviceDAO;

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;


    @RequestMapping(value="/seeWaitingList/{ID}", method={RequestMethod.GET}, headers="Accept=*/*", produces = "application/json")


    public @ResponseBody
    List<Property> execute(@PathVariable(value="ID") Long ID ){//ID - this agent ID;


        User user = new User();
        List<Property>propertiesInWaitingList=new ArrayList<>();
        List<Property>propertiesToShow = new ArrayList<>();


        try {


                propertiesInWaitingList = serviceDAO.seeWaitingList(ID);//see waiting list of this agent;

                for(Property prop:propertiesInWaitingList){
                    Property property = new Property();
                    property.setPropertyId(prop.getPropertyId());
                    property.setPropertyDescription(prop.getPropertyDescription());
                    property.setPrice(prop.getPrice());
                    property.setArea(prop.getArea());
                    property.setLandArea(prop.getLandArea());
                   // property.setClient(prop.getClient());
                    propertiesToShow.add(property);
                }





        }  catch (DBException e) {
            System.out.println("Error!");
        }

        //return propertiesOfThisUser;
        return propertiesToShow;

    }

}


