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
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static lv.javaguru.java2.domain.Statuss.CLIENT;
import static lv.javaguru.java2.domain.Statuss.JUNIOR;
@Controller
public class AgencyLoginController implements MVCController {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private AgentDAO agentDao;

    @Autowired
    private PropertyDAO propertyDao;

    public MVCModel execute(HttpServletRequest request) {

        //private static UserDAO userDao = new UserDAOImpl();

        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");

        try {
            Statuss statuss = checkCredentials(userName, userPassword);

           switch(statuss){
                case CLIENT:


                   User  user = userDao.findUserByCredentials(userName, userPassword);
                    if(user!=null){
                        List<Property>clientProperties= propertyDao.findPropertyByClient(user);
                        user.setListOfProperties(clientProperties);
                        String userFirstName=user.getFirstName();
                        String userLastName=user.getLastName();
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        session.setAttribute("userFirstName", userFirstName);
                        session.setAttribute("userLastName", userLastName);
                        session.setAttribute("userName", userName);
                        session.setAttribute("userPassword", userPassword);
                        session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
                        session.setAttribute("agentLastName", user.getAgent().getAgentLastName());
                    }
                    break;
                case JUNIOR:

                    Agent juniorAgent = agentDao.findAgentByCredentials(userName, userPassword);
                   /* if(juniorAgent!=null){
                        List<Property>clientProperties= propertyDao.findPropertyByClient(user);
                        user.setListOfProperties(clientProperties);
                        String userFirstName=user.getFirstName();
                        String userLastName=user.getLastName();
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        session.setAttribute("userFirstName", userFirstName);
                        session.setAttribute("userLastName", userLastName);
                        session.setAttribute("userName", userName);
                        session.setAttribute("userPassword", userPassword);
                        session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
                        session.setAttribute("agentLastName", user.getAgent().getAgentLastName());
                    }*/


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



}
