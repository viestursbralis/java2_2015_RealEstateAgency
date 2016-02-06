package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 28-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.UtilityDAO;
import lv.javaguru.java2.database.jdbc.ServiceDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.Utility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RestController
@Transactional
@EnableWebMvc
public class SeeWaitingListDetailsController {//Ajax controller;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_ServiceDAO")
    private ServiceDAO serviceDAO;

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;


    @Autowired
    @Qualifier("ORM_UtilityDAO")
    private UtilityDAO utilityDao;




    @RequestMapping(value="/propertyApproveForm", method={RequestMethod.GET}, headers="Accept=*/*")
    public Model execute(@RequestParam("ID") Long ID, Model model, HttpServletRequest request ) {//ID - in this case this is a property ID;
            Session session = sessionFactory.getCurrentSession();

        Property property = new Property();

        try {
            property = propertyDao.findPropertyById(ID);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("property", property);
        }catch (DBException e) {
            System.out.println("Error!");
        }

        session.evict(property);
/******************************************************************************************/
        List<Utility> utils = property.getPropertyUtilities();
        System.out.println("Util list from waiting list controller before:");
        System.out.println(utils.toString());
        List<Utility> allUtilities = new ArrayList<>();
            try {
                 allUtilities = utilityDao.getAllUtilities();
            }catch (DBException e) {
                    System.out.println("Error!");
                }



        for(int i=0; i<allUtilities.size(); i++){
            Long id = allUtilities.get(i).getUtilityId();
            for(Utility u:utils){Long shortId = u.getUtilityId();
                if (id==shortId){allUtilities.get(i).setChecked(true);}

            }
        }
        System.out.println("Util list from waiting list controller after:");
        System.out.println("Utils: "+utils.toString());
        System.out.println("allUtils: "+allUtilities.toString());
        utils.clear();
        property.setPropertyUtilities(allUtilities);
System.out.println("Property from SeeWaitingListDetailsController:"+property.toString());


        /******************************************************************************************/
        List<Photo> allPhotos = new ArrayList<>();
        allPhotos = property.getPropertyPhotos();

        for(Photo photo:allPhotos){

            photo.setChecked(true);
        }
        property.setPropertyPhotos(allPhotos);


/*********************************************************************************************/
        if (property!=null) {
            model.addAttribute("propertyApprovalModel", property);

        }



            return model;
    }
}



