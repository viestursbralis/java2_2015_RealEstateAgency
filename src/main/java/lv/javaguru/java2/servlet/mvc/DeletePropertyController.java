package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;
@Controller

@Transactional
public class DeletePropertyController  {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;
    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @RequestMapping(value="deleteProperty", method={RequestMethod.GET})//Ajax controller
    public ModelAndView execute(HttpServletRequest request ) {

        HttpSession session = request.getSession();
        String userName=(String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");
        User user = new User();
        List<Property>clientProperties = new ArrayList<>();
        try {
            user = userDao.findUserByCredentials(userName, userPassword );
            if (user != null) {
                clientProperties = propertyDao.findPropertyByClient(user);
                user.setListOfProperties(clientProperties);

            }
        }  catch (DBException e) {
            System.out.println("Error!");
        }

        List<Property> propertiesOfThisUser = user.getListOfProperties();
        return new ModelAndView("deleteSpecificProperty", "model", propertiesOfThisUser);

    }


    @RequestMapping(value="deleteProperty/{ID}", method={RequestMethod.GET}, headers="Accept=*/*", produces = "application/json")//Ajax controller
    public @ResponseBody  Property execute1(@PathVariable(value="ID") Long ID ){//ID-property id;
//a.k.a. seeDetails of property which we want to delete;
            Property prop = new Property();
                try {
                        prop = propertyDao.findPropertyById(ID);
                    }catch (DBException e) {
                        System.out.println("Error!");

                    }
                return prop;
                }

    @RequestMapping(value="deleteProperty/delete/{ID}", method={RequestMethod.GET}, headers="Accept=*/*", produces = "application/json")//Ajax controller
    public @ResponseBody  ModelAndView execute2(@PathVariable(value="ID") Long ID, HttpServletRequest request ) {
        HttpSession session = request.getSession();
        String userName=(String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");
        User user = new User();
        List<Property>clientProperties = new ArrayList<>();

        Property propertyToDelete = new Property();

        String message = "";

            try {
                propertyToDelete = propertyDao.findPropertyById(ID);
                List<PropertyOwner>owners = propertyToDelete.getPropertyOwners();
                List<Utility>utilities = propertyToDelete.getPropertyUtilities();
                List<Photo>photos = propertyToDelete.getPropertyPhotos();
                owners.clear();
                utilities.clear();
                photos.clear();
                propertyDao.delete(ID);
                message = "Property Nr."+ID+" deleted successfully!";
                user = userDao.findUserByCredentials(userName, userPassword );
                if (user != null) {
                    clientProperties = propertyDao.findPropertyByClient(user);
                    user.setListOfProperties(clientProperties);
                }
            } catch (DBException e) {
                System.out.println("Error!");

            }

        List<Property> propertiesOfThisUser = user.getListOfProperties();
ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("model", propertiesOfThisUser);
        modelAndView.addObject("message", message);
        modelAndView.setViewName("/deleteSpecificProperty");

        return modelAndView;

        }



    @RequestMapping(value = "/submitEdited/delete", method = {RequestMethod.POST}, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody String delete(@RequestBody PropertyDeleteJson propertyToDeleteJson)
    {

        Long idToDelete = propertyToDeleteJson.getPropertyId();
        Property propertyToDelete = new Property();
        try {
            propertyToDelete = propertyDao.findPropertyById(idToDelete);
            List<PropertyOwner>owners = propertyToDelete.getPropertyOwners();
            List<Utility>utilities = propertyToDelete.getPropertyUtilities();
            List<Photo>photos = propertyToDelete.getPropertyPhotos();
            owners.clear();
            utilities.clear();
            photos.clear();
            propertyDao.delete(idToDelete);
        }catch (DBException e) {
            System.out.println("Error!");
        }
        String message = "";
if (idToDelete!=null){
    message = "Property Nr."+idToDelete+" was deleted successfully!";
}


        return message;
    }









}

