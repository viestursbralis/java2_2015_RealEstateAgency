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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class SearchForSpecificUserController implements TransactionalController {

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDao;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;



    public MVCModel execute(HttpServletRequest request) {
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


        return new MVCModel("Login data", "/juniorAgentLoggedInFirstPage1.jsp");
    }

}

