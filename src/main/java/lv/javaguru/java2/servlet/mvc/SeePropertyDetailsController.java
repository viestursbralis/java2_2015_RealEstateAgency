package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 12-Jan-16.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.PropertyDeleteJson;
import lv.javaguru.java2.domain.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;



@Controller

@Transactional
public class SeePropertyDetailsController  {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @RequestMapping(value="seePropertyDetails/{ID}", method={RequestMethod.GET},
            headers="Accept=*/*", produces = "application/json")//AJAX controller
    public @ResponseBody  Property execute(@PathVariable(value="ID") Long ID) {

                Property prop=new Property();
                try {
                    prop=propertyDao.findPropertyById(ID);
                }catch(Exception ex) {
                    System.out.println(ex);
                }


        return prop;
    }




}

