package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
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
/*public class ListByUserController implements TransactionalController {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    private String filePath;
    private final String SAVE_DIR = "PropertyPhotos";


    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute("seeDetails", "false");

        String userName=(String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");
        User user = new User();
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





        //User user=(User)session.getAttribute("user");

        List<Property> propertiesOfThisUser = user.getListOfProperties();

              if(request.getParameter("seeDetails")!=null && request.getParameter("seeDetails").equals("true")){

            session.setAttribute("seeDetails", "true");
            String idToSee=request.getParameter("ID");

            Object id=request.getAttribute("ID");
            Long idToSee1 = Long.parseLong(String.valueOf(idToSee));
            filePath =request.getServletContext().getRealPath("");
            //request.getServletContext().getInitParameter("uploadFiles");
            session.setAttribute("imagePath", request.getServletContext().getRealPath("PropertyPhotos") + "/PropertyPhotos/188.jpg");
            String savePath = filePath + "\\" + SAVE_DIR +"\\";
            session.setAttribute("savePath", savePath);
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

        if (request.getParameter("seeDetails")!=null&&request.getParameter("seeDetails").equals("false")){
            session.setAttribute("seeDetails", "false");
            //session.setAttribute("ID", null);


        }





            return new MVCModel(propertiesOfThisUser, "/listPropertyByUser1.jsp");


    }

}*/
@Transactional
public class ListByUserController  {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    private String filePath;
    private final String SAVE_DIR = "PropertyPhotos";

    @RequestMapping(value="listByUser", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        session.setAttribute("seeDetails", "false");
/******************************************************************************/
        String userName=(String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");
        User user = new User();
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




        /**************************************************************************************/
        //User user=(User)session.getAttribute("user");

        List<Property> propertiesOfThisUser = user.getListOfProperties();

       /* if (request.getParameter("seeDetails")!=null&&request.getParameter("seeDetails").equals("true")){
            session.removeAttribute("idToSee");
            session.setAttribute("seeDetails", "true");
            Long idToSee=(Long)request.getAttribute("ID");
            String id=request.getParameter("ID");
            //Long id=Long.valueOf(ID).longValue();
            session.setAttribute("idToSee", id);
            session.setAttribute("idToSee1", idToSee);
            }*/

        if(request.getParameter("seeDetails")!=null && request.getParameter("seeDetails").equals("true")){

            session.setAttribute("seeDetails", "true");
            String idToSee=request.getParameter("ID");

            Object id=request.getAttribute("ID");
            Long idToSee1 = Long.parseLong(String.valueOf(idToSee));
            filePath =request.getServletContext().getRealPath("");
            //request.getServletContext().getInitParameter("uploadFiles");
            session.setAttribute("imagePath", request.getServletContext().getRealPath("PropertyPhotos") + "/PropertyPhotos/188.jpg");
            String savePath = filePath + "\\" + SAVE_DIR +"\\";
            session.setAttribute("savePath", savePath);
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

        if (request.getParameter("seeDetails")!=null&&request.getParameter("seeDetails").equals("false")){
            session.setAttribute("seeDetails", "false");
            //session.setAttribute("ID", null);


        }
        return new ModelAndView("listPropertyByUser1",  "model", propertiesOfThisUser);


    }

}