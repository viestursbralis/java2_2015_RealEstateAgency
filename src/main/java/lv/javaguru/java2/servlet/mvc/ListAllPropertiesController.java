package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ListAllPropertiesController implements TransactionalController {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;
    public MVCModel execute(HttpServletRequest request) {
        List<Property> allProperties = new ArrayList<>();
        HttpSession session = request.getSession();

        session.setAttribute("seeDetails", "false");
        try {
            allProperties = propertyDao.findAllProperties();
            if(request.getParameter("seeDetails")!=null && request.getParameter("seeDetails").equals("true")){

                session.setAttribute("seeDetails", "true");
                String idToSee=request.getParameter("ID");

                Long idToSee1 = Long.parseLong(String.valueOf(idToSee));

                Property prop=new Property();
                try {
                    prop=propertyDao.findPropertyById(idToSee1);

                    List<Utility>utilities = prop.getPropertyUtilities();

                    session.setAttribute("prop", prop);
                    session.setAttribute("utils", utilities);
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
     }catch (DBException e) {
            System.out.println("Error!");
        }


        return new MVCModel(allProperties, "/listAllProperties.jsp");
    }


}
