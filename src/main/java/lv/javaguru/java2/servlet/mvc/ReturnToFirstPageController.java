package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.domain.Statuss;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReturnToFirstPageController implements TransactionalController {


    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Statuss statuss = (Statuss)session.getAttribute("status");

        switch(statuss) {
            case CLIENT:
                return new MVCModel("Login data", "/clientLoggedInFirstPage1.jsp");
            case JUNIOR:
                return new MVCModel("Login data", "/juniorAgentLoggedInFirstPage1.jsp");
        }

        return new MVCModel("Login data", "/index.jsp");
    }

}
