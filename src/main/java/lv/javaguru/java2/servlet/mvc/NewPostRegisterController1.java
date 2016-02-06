package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 09-Jan-16.
 */

import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.*;
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
import java.util.Enumeration;
import java.util.List;


@Controller
@Transactional
@RequestMapping(value="newPost")
public class NewPostRegisterController1  {

    @Autowired
    @Qualifier("JDBC_CategoryDAO")
    private CategoryDAO categoryDao;

    @Autowired
    @Qualifier("JDBC_UtilityDAO")
    private UtilityDAO propertyUtilityDao;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;



    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
        String userPassword = (String)session.getAttribute("userPassword");

        if(userName!=null&&userPassword!=null){

            if(request.getParameter("start")!=null && request.getParameter("start").equals("start")){
                return new ModelAndView("newAddRegister", "model", null);
            }

            if(request.getParameter("addProperty")!=null&&request.getParameter("addProperty").equals("Next page")){
                session.setAttribute("category", request.getParameter("postType"));
                session.setAttribute("description", request.getParameter("description"));
                session.setAttribute("price", request.getParameter("price"));
                session.setAttribute("adress", request.getParameter("adress"));
                session.setAttribute("livingArea", request.getParameter("livingArea"));
                session.setAttribute("countOfBedrooms", request.getParameter("countOfBedrooms"));
                session.setAttribute("landArea", request.getParameter("landArea"));



                return new ModelAndView("newOwnersRegister", "model", null);
            }

            if(request.getParameter("addOwner")!=null && request.getParameter("addOwner").equals("Next page")){
                System.out.println("Request:" +request.toString());
                Enumeration params = request.getParameterNames();
                while(params.hasMoreElements()){
                    String paramName = (String)params.nextElement();
                    System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
                }
                String[] firstName = request.getParameterValues("firstName[]");
                String[] lastName = request.getParameterValues("lastName[]");
                String[] email = request.getParameterValues("email[]");
                String[] phone = request.getParameterValues("phone[]");
                List<PropertyOwner>listOfPropertyOwners = new ArrayList<>();
                for(int i=0; i<firstName.length; i++ ){
                    PropertyOwner owner = new PropertyOwner();
                    owner.setFirstName(firstName[i]);
                    owner.setLastName(lastName[i]);
                    owner.setOwnerEmail(email[i]);
                    owner.setOwnerPhone(phone[i]);
                    listOfPropertyOwners.add(owner);

                }
                session.setAttribute("ownerList", listOfPropertyOwners);




                return new ModelAndView("newUtilityRegister", "model", null);
            }

            if(request.getParameter("addUtility")!=null && request.getParameter("addUtility").equals("Next page")){
                Long i=new Long(0);
                Long cg=new Long(0);
                Long ch=new Long(0);
                Long cw=new Long(0);
                Long cs=new Long(0);
                Long checked= new Long(1);


                if(request.getParameter("internet")!=null){i=checked;}

                if(request.getParameter("city_gas")!=null) {cg=checked;}

                if(request.getParameter("city_heat")!=null){ch=checked; }

                if(request.getParameter("city_water")!=null){cw=checked; }

                if(request.getParameter("city_sewer")!=null){cs=checked; }


                List<Long> utilities=new ArrayList<>();

                utilities.add(i);
                utilities.add(cg);
                utilities.add(ch);
                utilities.add(cw);
                utilities.add(cs);

                List<Utility> propertyUtils =new ArrayList<>();
                try{
                    propertyUtils=propertyUtilityDao.createUtility(utilities);
                }catch (DBException e) {
                    System.out.println("Error!");
                }

                session.setAttribute("utils", propertyUtils);
                return new ModelAndView("imageUploadPage4", "model", null);
            }

            return new ModelAndView("clientLoggedInFirstPage1", "model", null);
        }


        return new ModelAndView("index", "model", null);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView saveToDatabase(HttpServletRequest request) {

        /* - from this point further data are saved into database - */
        HttpSession session = request.getSession();


            Property property=new Property();
            User user =new User();
            User userOfThisSession = (User)session.getAttribute("user");
            user.setUserId(userOfThisSession.getUserId());
            user.setFirstName(userOfThisSession.getFirstName());
            user.setLastName(userOfThisSession.getLastName());
            user.setUserEmail(userOfThisSession.getUserEmail());
            user.setPassword(userOfThisSession.getPassword());
            user.setStatuss(userOfThisSession.getStatuss());
            property.setClient(user);
            CategoryName categoryName = CategoryName.valueOf((String)session.getAttribute("category"));
            Category category=new Category();

            try {
                category = categoryDao.findCategoryByName(categoryName);
                property.setCategory(category);
            }catch (DBException e) {
                System.out.println("Error!");
            }

            property.setPropertyDescription((String)session.getAttribute("description"));
            property.setPrice((Double.parseDouble((String)session.getAttribute("price"))));
            property.setAdress((String)session.getAttribute("adress"));
            property.setArea((Long.parseLong((String)session.getAttribute("livingArea"), 10)));
            property.setCountOfBedrooms(Integer.parseInt((String)session.getAttribute("countOfBedrooms")));
            property.setLandArea((Long.parseLong((String)session.getAttribute("landArea"))));
            property.setPostStatuss(PostStatuss.WAITING);

             List<PropertyOwner>owners = (List<PropertyOwner>)session.getAttribute("ownerList");
            property.setPropertyOwners(owners);


        List<Utility>utils= (List<Utility>)session.getAttribute("utils");
            property.setPropertyUtilities(utils);//list of utilities is set to this property;

/*********************************************************************************************************/
/* image upload and final property saving to database*/
            ImageUploadController4 im = new ImageUploadController4();
            List<String> photoNames = im.execute(request);//List<String>photoNames - because there are more than 1 photo;
        List<Photo>photos = new ArrayList<>();
        for(String photoName:photoNames){
            Photo photo = new Photo();
            photo.setPhotoName(photoName);

            try{
                propertyDao.insertPhoto(photo);
            }catch (DBException e) {
                System.out.println("Error!");
            }

            photos.add(photo);

        }

             property.setPropertyPhotos(photos);
            try {
                propertyDao.createProperty(property);
            }catch (DBException e) {
                System.out.println("Error!");
            }



            /******************************************************************************************************/
            //session.removeAttribute("postSuccessfull");
            String postSuccessfull="Your post was successfully registered in our waiting list! Your post will be checked by our agent and you will " +
                    "receive a confirmation email within 2 working days!";
            session.setAttribute("postSuccessfull", postSuccessfull);




        return new ModelAndView("clientLoggedInFirstPage1", "model", null);
    }



}





