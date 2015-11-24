package lv.javaguru.java2.servlet.mvc;


import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Statuss;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class NewClientRegisterController implements MVCController {

    @Autowired
    AgentDAO agentDao;
    @Autowired
    UserDAO userDao;


    public MVCModel execute(HttpServletRequest request) {

        if (request.getMethod() == "GET") {

            return new MVCModel("Login data", "/newClientRegister.jsp");
        }

        if (request.getMethod() == "POST") {


            String userFirstName = request.getParameter("firstName");
            String userLastName = request.getParameter("lastName");
            String userEmail = request.getParameter("email");
            String userPassword = request.getParameter("password");



            User newUser= new User();
            Agent lessBusyAgent = new Agent();
            try {
                lessBusyAgent = agentDao.findLessBusyAgent();
            }catch (DBException e) {
                System.out.println("Error!");
            }

            newUser.setFirstName(userFirstName);
            newUser.setLastName(userLastName);
            newUser.setUserEmail(userEmail);
            newUser.setPassword(userPassword);
            newUser.setStatuss(Statuss.CLIENT);
            newUser.setAgent(lessBusyAgent);
            String agentFirstName=newUser.getAgent().getAgentFirstName();
            String agentLastName=newUser.getAgent().getAgentLastName();
            try {
                userDao.create(newUser);

                HttpSession session = request.getSession();
                session.setAttribute("userFirstName", userFirstName);
                session.setAttribute("userLastName", userLastName);
                session.setAttribute("agentFirstName", agentFirstName);
                session.setAttribute("agentLastName", agentLastName);

            } catch (DBException e) {
                System.out.println("Error!");
            }
            return new MVCModel("Login data", "/clientLoggedInFirstPage.jsp");
        }
        return new MVCModel("Login data", "/index.jsp");
    }
}




