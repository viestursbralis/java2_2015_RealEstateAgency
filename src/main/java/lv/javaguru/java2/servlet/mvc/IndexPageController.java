package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexPageController implements MVCController {

    public MVCModel execute(HttpServletRequest request) {



        return new MVCModel("Index page", "/index.jsp");
    }

}
