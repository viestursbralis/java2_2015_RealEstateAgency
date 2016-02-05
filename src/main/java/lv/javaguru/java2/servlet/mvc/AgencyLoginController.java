package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.Statuss;
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
import java.util.List;

import static lv.javaguru.java2.domain.Statuss.CLIENT;
import static lv.javaguru.java2.domain.Statuss.JUNIOR;
@Controller
/*public class AgencyLoginController implements TransactionalController {

    @Autowired @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @Autowired
    private AgentDAO agentDao;

    @Autowired @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    public MVCModel execute(HttpServletRequest request) {

        //private static UserDAO userDao = new UserDAOImpl();

        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");

        try {
            Statuss statuss = checkCredentials(userName, userPassword);
            HttpSession session = request.getSession();
            session.setAttribute("status", statuss);


           switch(statuss){
                case CLIENT:


                   User  user = userDao.findUserByCredentials(userName, userPassword);
                    if(user!=null){
                        List<Property>clientProperties= propertyDao.findPropertyByClient(user);

                        user.setListOfProperties(clientProperties);
                        String userFirstName=user.getFirstName();
                        String userLastName=user.getLastName();

                        session.setAttribute("user", user);
                        session.setAttribute("userFirstName", userFirstName);
                        session.setAttribute("userLastName", userLastName);
                        session.setAttribute("userName", userName);
                        session.setAttribute("userPassword", userPassword);

                        session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
                        session.setAttribute("agentLastName", user.getAgent().getAgentLastName());
                    }

                    return new MVCModel("Login data", "/clientLoggedInFirstPage1.jsp");

                case JUNIOR:

                    Agent juniorAgent = agentDao.findAgentByCredentials(userName, userPassword);
                   if(juniorAgent!=null){
                        List<User>users = userDao.findAllUsersOfThisAgent(juniorAgent);

                       session.setAttribute("juniorAgent",juniorAgent);
                        session.setAttribute("agentFirstName", juniorAgent.getAgentFirstName());
                       session.setAttribute("agentLastName", juniorAgent.getAgentLastName());
                        session.setAttribute("userName", userName);
                       session.setAttribute("password", userPassword);

                    }
                    return new MVCModel("Login data", "/juniorAgentLoggedInFirstPage1.jsp");

               case SENIOR:
                   Agent seniorAgent = agentDao.findAgentByCredentials(userName, userPassword);

                   if(seniorAgent!=null){

                       session.setAttribute("seniorAgent", seniorAgent);


                   }
                   return new MVCModel("Login data", "/seniorAgentLoggedInFirstPage1.jsp");


           }
        }        catch (DBException e) {
            System.out.println("Error!");
        }

        return new MVCModel("Login data", "/clientLoggedInFirstPage1.jsp");

    }


    private Statuss checkCredentials(String username, String password) throws DBException {
        Statuss statuss = null;



        if (agentDao.findAgentByCredentials(username, password).getAgentStatuss()==Statuss.JUNIOR) {

            statuss = JUNIOR;
            return statuss;
        }
        else if (agentDao.findAgentByCredentials(username, password).getAgentStatuss()==Statuss.SENIOR){

            statuss = JUNIOR;
            return statuss;
        }
        else if (userDao.findUserByCredentials(username, password).getStatuss()==Statuss.CLIENT) {
            statuss=CLIENT;
            return statuss;
        }

        return statuss;
    }



}*/

@Transactional
public class AgencyLoginController  {

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @Autowired
    @Qualifier("ORM_AgentDAO")
    private AgentDAO agentDao;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @RequestMapping(value="login", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        //private static UserDAO userDao = new UserDAOImpl();

        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");

        try {
            Statuss statuss = checkCredentials(userName, userPassword);
            HttpSession session = request.getSession();
            session.setAttribute("status", statuss);


            switch (statuss) {
                case CLIENT:


                    User user = userDao.findUserByCredentials(userName, userPassword);
                    if (user != null) {
                        List<Property> clientProperties = propertyDao.findPropertyByClient(user);

                        user.setListOfProperties(clientProperties);
                        String userFirstName = user.getFirstName();
                        String userLastName = user.getLastName();

                        session.setAttribute("user", user);
                        session.setAttribute("userFirstName", userFirstName);
                        session.setAttribute("userLastName", userLastName);
                        session.setAttribute("userName", userName);
                        session.setAttribute("userPassword", userPassword);

                        session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
                        session.setAttribute("agentLastName", user.getAgent().getAgentLastName());
                    }

                    return new ModelAndView("clientLoggedInFirstPage1", "model", null);

                case JUNIOR:

                    Agent juniorAgent = agentDao.findAgentByCredentials(userName, userPassword);
                    if (juniorAgent != null) {
                        List<User> users = userDao.findAllUsersOfThisAgent(juniorAgent);

                        session.setAttribute("juniorAgent", juniorAgent);
                        session.setAttribute("agentFirstName", juniorAgent.getAgentFirstName());
                        session.setAttribute("agentLastName", juniorAgent.getAgentLastName());
                        session.setAttribute("userName", userName);
                        session.setAttribute("password", userPassword);
                        session.setAttribute("agentId", juniorAgent.getAgentId());

                    }
                    return new ModelAndView("juniorAgentLoggedInFirstPage1", "model", null);

                case SENIOR:
                    Agent seniorAgent = agentDao.findAgentByCredentials(userName, userPassword);

                    if (seniorAgent != null) {

                        session.setAttribute("seniorAgent", seniorAgent);


                    }
                    return new ModelAndView("seniorAgentLoggedInFirstPage1", "model", null);


            }
        } catch (DBException e) {
            System.out.println("Error!");
        }

        return new ModelAndView("clientLoggedInFirstPage1.jsp", "model", null);

    }


    private Statuss checkCredentials(String username, String password) throws DBException {
        Statuss statuss = null;


        if (agentDao.findAgentByCredentials(username, password)!=null &&
                agentDao.findAgentByCredentials(username, password).getAgentStatuss() == Statuss.JUNIOR) {

            statuss = JUNIOR;
            return statuss;
        } else if (agentDao.findAgentByCredentials(username, password)!=null &&
                agentDao.findAgentByCredentials(username, password).getAgentStatuss() == Statuss.SENIOR) {

            statuss = JUNIOR;
            return statuss;
        } else if (userDao.findUserByCredentials(username, password)!=null &&
        userDao.findUserByCredentials(username, password).getStatuss() == Statuss.CLIENT) {
            statuss = CLIENT;
            return statuss;
        }

        return statuss;
    }
}



