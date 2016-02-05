package lv.javaguru.java2.servlet.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Viesturs on 05-Jan-16.
 */
@Controller
@RequestMapping("/sendEmail")
public class SendEmailController {

    @Autowired
    private JavaMailSender mailSender;


    @RequestMapping(method={RequestMethod.GET})
    public ModelAndView getEmailForm(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sendEmail");
        modelAndView.addObject("model", null);


        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST)
    public String doSendEmail(HttpServletRequest request) {
        // takes input from e-mail form
        String recipientAddress = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        //dynamically retrieve agent email credentials from database and set to MailConfig.class - how?


        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);


        JavaMailSenderImpl jMailSender = (JavaMailSenderImpl)mailSender;

        //jMailSender.setUsername();
        //jMailSender.setPassword();
        // sends the e-mail
        mailSender.send(email);

        // forwards to the view named "Result"
        return "Result";
    }
}
