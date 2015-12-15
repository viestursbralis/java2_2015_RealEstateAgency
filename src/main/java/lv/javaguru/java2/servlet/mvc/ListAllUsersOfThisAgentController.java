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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;






@Controller
public class ListAllUsersOfThisAgentController implements TransactionalController {

    @Autowired
    @Qualifier("JDBC_UserDAO")
    UserDAO userDao = new UserDAOImpl();
    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;
    public MVCModel execute(HttpServletRequest request) {
        List<User> allUsers = new ArrayList<>();
        HttpSession session = request.getSession();
        Agent agent = (Agent)session.getAttribute("juniorAgent");
        session.setAttribute("seeUserDetails", "false");
        try {
            allUsers = userDao.findAllUsersOfThisAgent(agent);

        }catch (DBException e) {
            System.out.println("Error!");
        }


        return new MVCModel(allUsers, "/listUsersByAgents1.jsp");
    }

}


