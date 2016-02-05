package lv.javaguru.java2.servlet.servlets;

import lv.javaguru.java2.servlet.mvc.MailConfig;
import lv.javaguru.java2.servlet.mvc.SpringConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * Created by Viesturs on 18-Dec-15.
 */
public class SpringWebMvcInitializer extends AbstractDispatcherServletInitializer {


    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(SpringConfig.class);
        applicationContext.register(MailConfig.class);
        return applicationContext;
    }
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebMVCConfig.class);
        return applicationContext;
    }
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
