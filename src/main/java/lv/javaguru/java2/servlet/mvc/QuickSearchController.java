package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 24-Nov-15.
 */

import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuickSearchController implements MVCController {

    @Autowired
    private PropertyDAO propertyDao;


    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();


        Property prop=new Property();


        if(request.getParameter("quickSearch")!=null && request.getParameter("quickSearch").equals("true")){

            session.setAttribute("quickSearch", "true");
            String idToSee=request.getParameter("ID");

            Object id=request.getAttribute("ID");
            Long idToSee1 = Long.parseLong(String.valueOf(idToSee));


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

            }catch(Exception ex) {
                System.out.println(ex);
            }

        }


        return new MVCModel(prop, "/index.jsp");


    }

}

