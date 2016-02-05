package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
/*public class LogOutController implements TransactionalController {


    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();



        return new MVCModel("Log out data", "/index.jsp");
    }

}*/
@Transactional
public class LogOutController  {

    @RequestMapping(value="logout", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse repsonse) {
        HttpSession session = request.getSession();
        session.invalidate();



        return new ModelAndView("index", "model", null);
    }

}
