package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 14-Dec-15.
 */

import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;



@Controller
public class ListOfUserPropertiesAjax  {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    public @ResponseBody List<Property> execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute("seeDetails", "false");
        List<Property> prop = new ArrayList<>();
/******************************************************************************/

/*
        if (request.getParameter("seeDetails") != null && request.getParameter("seeDetails").equals("true")) {

            session.setAttribute("seeDetails", "true");
            session.setAttribute("propertyDetails", "true");
            String idToSee = request.getParameter("ID");


            Long idToSee1 = Long.parseLong(String.valueOf(idToSee));


            try {
                prop = propertyDao.findPropertyById(idToSee1);

                session.setAttribute("prop", prop);
                List<Photo> propertyPhotos = propertyDao.findAllPropertyPhotoss(idToSee1);
                List<String> photoNames = new ArrayList<>();
                for (Photo photo : propertyPhotos) {
                    String photoName = photo.getPhotoName();

                    photoNames.add(photoName);

                }
                session.setAttribute("photos", propertyPhotos);
                session.setAttribute("photoNames", photoNames);
                session.setAttribute("propertyDetails", "true");
            } catch (Exception ex) {
                System.out.println(ex);
            }
            //Long id=Long.valueOf(ID).longValue();
            session.setAttribute("idToSee", idToSee);//working;
            session.setAttribute("idToSee1", idToSee1);//working;

            //return new MVCModel(prop, "/listPropertyByUser1.jsp");
        }

        if (request.getParameter("seeDetails") != null && request.getParameter("seeDetails").equals("false")) {
            session.setAttribute("seeDetails", "false");
            //session.setAttribute("ID", null);


        }

*/
        return prop;


    }

}


