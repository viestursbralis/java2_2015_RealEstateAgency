package lv.javaguru.java2.servlet.mvc;


import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Statuss;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
/*public class NewClientRegisterController implements TransactionalController {

    @Autowired
    AgentDAO agentDao;
    @Autowired @Qualifier("ORM_UserDAO")
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
                userDao.createNewUserInDatabase(newUser);

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
}*/
@Transactional
public class NewClientRegisterController  {

    @Autowired
    @Qualifier ("ORM_AgentDAO")
    AgentDAO agentDao;
    @Autowired @Qualifier("ORM_UserDAO")
    UserDAO userDao;

/*@RequestMapping(value="newClientRegister", method={RequestMethod.GET})
    public ModelAndView executeGet(HttpServletRequest request, HttpServletResponse repsonse) {



            return new ModelAndView("newClientRegister", "model", null);
        }*/


    @RequestMapping(value="newClientRegister1", method={RequestMethod.GET}, headers = "Accept=*/*", produces = "application/json" )
    public @ResponseBody Model getUserRegistrationForm(Model model) {
model.addAttribute("userRegisterModel", new User());
            //return new ModelAndView("newClientRegister1", "userRegisterModel", new User());
        return model;
        }



    /*@RequestMapping(value="newClientRegister", method={RequestMethod.POST})
    public ModelAndView executePost(HttpServletRequest request, HttpServletResponse repsonse) {


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
                userDao.createNewUserInDatabase(newUser);

                HttpSession session = request.getSession();
                session.setAttribute("userFirstName", userFirstName);
                session.setAttribute("userLastName", userLastName);
                session.setAttribute("agentFirstName", agentFirstName);
                session.setAttribute("agentLastName", agentLastName);

            } catch (DBException e) {
                System.out.println("Error!");
            }
            return new ModelAndView("clientLoggedInFirstPage1", "model", null);
        }*/

    @RequestMapping(value="newClientRegister", method={RequestMethod.POST})
    public ModelAndView processUserRegistrationForm(@ModelAttribute User user, HttpServletRequest request) {

        Agent lessBusyAgent = new Agent();
        try {
            lessBusyAgent = agentDao.findLessBusyAgent();
        }catch (DBException e) {
            System.out.println("Error!");
        }
        user.setAgent(lessBusyAgent);

        try {
            userDao.createNewUserInDatabase(user);

            HttpSession session = request.getSession();
            session.setAttribute("userFirstName", user.getFirstName());
            session.setAttribute("userLastName", user.getLastName());
            session.setAttribute("agentFirstName", user.getAgent().getAgentFirstName());
            session.setAttribute("agentLastName", user.getAgent().getAgentLastName());

        } catch (DBException e) {
            System.out.println("Error!");
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("clientLoggedInFirstPage1");

        modelAndView.addObject("registeredUser", user);

        return modelAndView;
    }



}


