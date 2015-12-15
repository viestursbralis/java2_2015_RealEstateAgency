package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.Utility;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viesturs on 14-Dec-15.
 */
@Transactional
@Component
public class Utils  {

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;
    @Transactional
    public List<Utility> utils(Long id){
        List<Utility> utilities = new ArrayList<>();
        try {
            Property prop = propertyDao.findPropertyById(id);
            Hibernate.initialize(prop.getPropertyUtilities());
            //or prop.getPropertyUtilities().size();
            utilities = prop.getPropertyUtilities();
        }catch (DBException e) {
            System.out.println("Error!");
        }
        return utilities;
    }




}
