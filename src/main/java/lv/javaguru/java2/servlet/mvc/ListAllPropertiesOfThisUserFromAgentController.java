package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 15-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.Hibernate.UserDAOImpl;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
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

@Transactional
public class ListAllPropertiesOfThisUserFromAgentController  {

    @Autowired
    @Qualifier("ORM_UserDAO")
    UserDAO userDao;
    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;
    @RequestMapping(value="listAllPropertiesOfThisUserFromAgent", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = new User();
        session.setAttribute("seeUserDetails", "false");
        String idToSee=request.getParameter("ID");
        Long idToSee1 = Long.parseLong(String.valueOf(idToSee));
        List<Property>propertiesOfThisUser = new ArrayList<>();
        try {

            if(request.getParameter("seeUserDetails")!=null && request.getParameter("seeUserDetails").equals("true")){

                session.setAttribute("seeUserDetails", "true");
                user = userDao.getUserById(idToSee1); //find user by id;
                session.setAttribute("userFirstName", user.getFirstName());
                session.setAttribute("userLastName", user.getLastName());
                propertiesOfThisUser = propertyDao.findPropertyByClient(user);
                session.setAttribute("propertiesOfThisUser", propertiesOfThisUser);
                //Long id=Long.valueOf(ID).longValue();
                session.setAttribute("idToSee", idToSee);//working;
                session.setAttribute("idToSee1", idToSee1);//working;
                session.setAttribute("propertiesOfThisUserFromAjax", "true");
                //return new MVCModel(prop, "/listPropertyByUser1.jsp");
            }
        }catch (DBException e) {
            System.out.println("Error!");
        }


        return new ModelAndView("listUsersByAgents1", "model", null);
    }

}


