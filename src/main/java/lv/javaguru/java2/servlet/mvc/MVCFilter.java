package lv.javaguru.java2.servlet.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MVCFilter implements Filter {

    //private Map<String, MVCController> controllers;
    private Map<String, TransactionalController> controllers;
    private ApplicationContext springContext;
    private static final Logger logger = Logger.getLogger(MVCFilter.class.getName());
    private TransactionalController getBean(Class clazz){
        return (TransactionalController) springContext.getBean(clazz);
    }
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            springContext =
                    new AnnotationConfigApplicationContext(SpringConfig.class);
        } catch (BeansException e) {
            logger.log(Level.INFO, "Spring context failed to start", e);
        }
        controllers = new HashMap<String, TransactionalController>();
        controllers.put("/hello", getBean(HelloWorldController.class));
        controllers.put("/index", getBean(IndexPageController.class));
        controllers.put("/login", getBean(AgencyLoginController.class));
        controllers.put("/newClientRegister", getBean(NewClientRegisterController.class));
        controllers.put("/clientsChoice", getBean(AgencyClientsChoiceController.class));
        controllers.put("/newPost", getBean(NewPostRegisterController.class));
        controllers.put("/deleteProperty", getBean(DeletePropertyController.class));
        controllers.put("/returnToFirstPage", getBean(ReturnToFirstPageController.class));
        controllers.put("/listAllProperties", getBean(ListAllPropertiesController.class));
        controllers.put("/search", getBean(SearchForSpecificPropertyController.class));
        controllers.put("/logout", getBean(LogOutController.class));
        controllers.put("/listByUser", getBean(ListByUserController.class));
        controllers.put("/imageUpload", getBean(ImageUploadController3.class));
        controllers.put("/quickSearch", getBean(QuickSearchController.class));
        controllers.put("/listAllUsersByAgents", getBean(ListAllUsersOfThisAgentController.class));
        controllers.put("/searchForSpecificUser", getBean(SearchForSpecificUserController.class));
        controllers.put("/listAllPropertiesOfThisUserFromAgent", getBean(ListByUserFromAgentController.class));//Ajax controller
    }
   /* public void init(FilterConfig filterConfig) throws ServletException {


        controllers = new HashMap<String, MVCController>();
        controllers.put("/hello", new HelloWorldController());
        controllers.put("/index", new IndexPageController());
        controllers.put("/login", new AgencyLoginController());
        controllers.put("/newClientRegister", new NewClientRegisterController());
        controllers.put("/clientsChoice", new AgencyClientsChoiceController());
        controllers.put("/newPost", new NewPostRegisterController());
        controllers.put("/deleteProperty", new DeletePropertyController());
        controllers.put("/returnToFirstPage", new ReturnToFirstPageController());
        controllers.put("/listAllProperties", new ListAllPropertiesController());
        controllers.put("/search", new SearchForSpecificPropertyController());
        controllers.put("/logout", new LogOutController());
        controllers.put("/listByUser", new ListByUserController());
        controllers.put("/imageUpload", new ImageUploadController3());

    }*/



    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String contextURI = req.getServletPath();
        MVCController controller = controllers.get(contextURI);
        if (controller != null) {
            MVCModel model = controller.execute(req);

            req.setAttribute("model", model.getData());

            ServletContext context = req.getServletContext();
            RequestDispatcher requestDispacher =
                    context.getRequestDispatcher(model.getViewName());
            requestDispacher.forward(req, resp);
        }
        else filterChain.doFilter(request,response);
    }

    public void destroy() {

    }

}
