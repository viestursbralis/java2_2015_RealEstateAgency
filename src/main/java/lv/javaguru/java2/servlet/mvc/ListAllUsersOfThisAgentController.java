package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 15-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.Hibernate.UserDAOImpl;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
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
public class ListAllUsersOfThisAgentController  {

    @Autowired
    @Qualifier("ORM_UserDAO")
    UserDAO userDao;
    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;
    @RequestMapping(value="listAllUsersByAgents", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        List<User> allUsers = new ArrayList<>();
        HttpSession session = request.getSession();
        Agent agent = (Agent)session.getAttribute("juniorAgent");
        session.setAttribute("seeUserDetails", "false");
        try {
            allUsers = userDao.findAllUsersOfThisAgent(agent);

        }catch (DBException e) {
            System.out.println("Error!");
        }


        return new ModelAndView("listUsersByAgents1", "model", allUsers );
    }

}
