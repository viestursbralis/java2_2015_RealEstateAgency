package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogOutController implements TransactionalController {


    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();



        return new MVCModel("Log out data", "/index.jsp");
    }

}
