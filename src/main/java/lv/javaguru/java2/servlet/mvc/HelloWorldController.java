package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorldController implements TransactionalController {

    public MVCModel execute(HttpServletRequest request) {
        return new MVCModel("Hello from MVC", "/helloWorld.jsp");
    }

}
