package lv.javaguru.java2.database.Hibernate;

/**
 * Created by Viesturs on 25-Jan-16.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UtilityDAO;
import lv.javaguru.java2.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Repository("ORM_UtilityDAO")
@Transactional
public class
UtilityDAOImpl implements UtilityDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Utility> getAllUtilities() throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Utility.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();


    }



    public List<Utility> createUtility(List<Long> utilityId) throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Utility.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

}


