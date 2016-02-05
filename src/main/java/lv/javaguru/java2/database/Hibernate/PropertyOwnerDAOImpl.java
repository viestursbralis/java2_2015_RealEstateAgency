package lv.javaguru.java2.database.Hibernate;

/**
 * Created by Viesturs on 28-Jan-16.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyOwnerDAO;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.PropertyOwner;
import lv.javaguru.java2.domain.Utility;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Repository("ORM_PropertyOwnerDAO")
@Transactional
public class PropertyOwnerDAOImpl implements PropertyOwnerDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Long createPropertyOwner(PropertyOwner owner) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        //session.persist(property);
        return (Long) session.save(owner);
    }

    @Override
    public PropertyOwner findPropertyOwnerById(Long ownerId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        PropertyOwner propertyOwner = (PropertyOwner) session.get(PropertyOwner.class, ownerId);

        return propertyOwner;
    }





    @Override
    public void updatePropertyOwner(PropertyOwner owner) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.merge(owner);
    }





}


