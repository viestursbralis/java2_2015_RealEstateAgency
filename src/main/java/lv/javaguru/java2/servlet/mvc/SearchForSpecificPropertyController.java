package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchForSpecificPropertyController implements MVCController {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private PropertyDAO propertyDao;



    public MVCModel execute(HttpServletRequest request) {


        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");


        User user=new User();
        try {
            user = userDao.findUserByCredentials(userName, userPassword);

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
        }        catch (DBException e) {
            System.out.println("Error!");
        }

        return new MVCModel("Login data", "/clientLoggedInFirstPage.jsp");
    }

}
