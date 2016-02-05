package lv.javaguru.java2.database.Hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.jdbc.ServiceDAO;
import lv.javaguru.java2.domain.PostStatuss;
import lv.javaguru.java2.domain.Property;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Viesturs on 27-Dec-15.
 */


@Repository("ORM_ServiceDAO")
@Transactional
public class ServiceDAOImpl implements ServiceDAO{
    @Autowired
    private SessionFactory sessionFactory;


    @Autowired
    @Qualifier("ORM_PropertyDAO")
    PropertyDAO propertyDao;


    @Override
    public List<Property> seeWaitingList(Long agentId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Property.class, "prop");
        c.createAlias("prop.client", "client");
        c.createAlias("client.agent", "agent");
        c.add(Restrictions.eq("agent.agentId", agentId));
        c.add(Restrictions.eq("prop.postStatuss", PostStatuss.WAITING));
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        //initialize(prop.getPropertyUtilities());
        return c.list();
    }

    public Property setAllPropertyLists(Long propertyId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class);

        Property property = propertyDao.findPropertyById(propertyId);
        Hibernate.initialize(property.getPropertyOwners());
        Hibernate.initialize(property.getPropertyUtilities());
        Hibernate.initialize(property.getPropertyPhotos());


       return property;
    }




}
