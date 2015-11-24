package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
public class DeletePropertyController implements MVCController {

    @Autowired
    private PropertyDAO propertyDao;
    @Autowired
    private UserDAO userDao;

    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName=(String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");
        User user = new User();

        if(request.getParameter("delete")!=null&&request.getParameter("delete").equals("delete")){


            try {
                user = userDao.findUserByCredentials(userName, userPassword );
                if (user != null) {
                    List<Property> clientProperties = propertyDao.findPropertyByClient(user);
                    user.setListOfProperties(clientProperties);
                    String userFirstName = user.getFirstName();
                    String userLastName = user.getLastName();
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    session.setAttribute("userFirstName", userFirstName);
                    session.setAttribute("userLastName", userLastName);
                    session.setAttribute("userName", userName);
                    session.setAttribute("userPassword", userPassword);
                    session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
                    session.setAttribute("agentLastName", user.getAgent().getAgentLastName());
                }
            }  catch (DBException e) {
                System.out.println("Error!");
            }

            List<Property> propertiesOfThisUser0 = user.getListOfProperties();
            return new MVCModel(propertiesOfThisUser0, "/deleteSpecificProperty.jsp");

        }


    if(request.getParameter("seeDetails")!=null && request.getParameter("seeDetails").equals("true")){

        session.setAttribute("seeDetails", "true");
        String idToSee=request.getParameter("ID");

        Object id=request.getAttribute("ID");
        Long idToSee1 = Long.parseLong(String.valueOf(idToSee));

        Property prop=new Property();
        try {
            prop=propertyDao.findPropertyById(idToSee1);

            session.setAttribute("prop", prop);
            List<Photo> propertyPhotos = propertyDao.findAllPropertyPhotoss(idToSee1);
            List<String> photoNames = new ArrayList<>();
            for(Photo photo:propertyPhotos){
                String photoName=photo.getPhotoName();

                photoNames.add(photoName);

            }
            session.setAttribute("photos", propertyPhotos);
            session.setAttribute("photoNames", photoNames);
            session.setAttribute("propertyDetails", "true");
        }catch(Exception ex) {
            System.out.println(ex);
        }
        //Long id=Long.valueOf(ID).longValue();
        session.setAttribute("idToSee", idToSee);//working;
        session.setAttribute("idToSee1", idToSee1);//working;

        //return new MVCModel(prop, "/listPropertyByUser1.jsp");
    }



if(request.getParameter("deleteFromPopup")!=null&&request.getParameter("deleteFromPopup").equals("true")) {
    String id = request.getParameter("ID");

    Long idToDelete = Long.valueOf(id).longValue();
    try {
        propertyDao.delete(idToDelete);
        String info = "Property deleted successfully!";

        session.setAttribute("deleted", info);
        session.removeAttribute("propertyDetails");
        //session.setAttribute("propertyDetails", null);

    } catch (DBException e) {
        System.out.println("Error!");

    }
}



        try {
             user = userDao.findUserByCredentials(userName, userPassword);
            List<Property>clientProperties= propertyDao.findPropertyByClient(user);
            user.setListOfProperties(clientProperties);
            session.setAttribute("user", user);


        }catch (DBException e) {
            System.out.println("Error!");

        }


        List<Property> propertiesOfThisUser = user.getListOfProperties();

        return new MVCModel(propertiesOfThisUser, "/deleteSpecificProperty.jsp");

    }

}
