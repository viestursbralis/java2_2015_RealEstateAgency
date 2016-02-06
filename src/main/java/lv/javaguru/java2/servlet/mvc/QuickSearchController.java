package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 24-Nov-15.
 */

import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Photo;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.PropertyDeleteJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller

@Transactional
public class QuickSearchController  {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @RequestMapping(value = "/quickSearch", method = {RequestMethod.POST}, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody  Property quickSearch(@RequestBody PropertyDeleteJson propertyToDeleteJson)
    {
        Long propertyToSeeId = propertyToDeleteJson.getPropertyId();
        Property prop=new Property();
        try {
            prop=propertyDao.findPropertyById(propertyToSeeId);
           // prop = propertyDao.findApprovedPropertyById(propertyToSeeId);
        }catch(Exception ex) {
            System.out.println(ex);
        }


        return prop;
    }
}