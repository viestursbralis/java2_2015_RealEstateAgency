package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.domain.Statuss;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller

@Transactional
public class ReturnToFirstPageController  {

    @RequestMapping(value="returnToFirstPage", method={RequestMethod.GET})
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Statuss statuss = (Statuss)session.getAttribute("status");

        switch(statuss) {
            case CLIENT:
                return new ModelAndView("clientLoggedInFirstPage1", "model", null);
            case JUNIOR:
                return new ModelAndView("juniorAgentLoggedInFirstPage1", "model", null);
        }

        return new ModelAndView("index", "model", null);
    }

}
