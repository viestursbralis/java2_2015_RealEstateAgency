package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
/*public class IndexPageController implements TransactionalController {

    public MVCModel execute(HttpServletRequest request) {



        return new MVCModel("Index page", "/index.jsp");
    }

}*/
@Transactional
public class IndexPageController {

    @RequestMapping(value="index", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("model", null);


        return modelAndView;
    }

}
