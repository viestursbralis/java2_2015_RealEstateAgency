package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 12-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
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


@Controller

@Transactional
public class SearchForSpecificUserController {

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;


    @RequestMapping(value="searchForSpecificUser", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if(request.getParameter("openSearchBox")!=null&&request.getParameter("openSearchBox").equals("openSearchBox")){
            session.setAttribute("openUserSearchPopUp", "true");


        }

        if(request.getParameter("userParameter")!=null) {
            try {
                User user = userDao.findUserLike(request.getParameter("userParameter"));
                session.setAttribute("user", user);


            }catch (DBException e) {
                System.out.println("Error!");
            }



        }


        return new ModelAndView("juniorAgentLoggedInFirstPage1", "model", null);
    }

}

