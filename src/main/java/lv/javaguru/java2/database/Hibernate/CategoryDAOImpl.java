package lv.javaguru.java2.database.Hibernate;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.jdbc.ServiceDAO;
import lv.javaguru.java2.domain.Category;
import lv.javaguru.java2.domain.CategoryName;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Viesturs on 27-Dec-15.
 */


@Repository("ORM_CategoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Autowired
    @Qualifier("ORM_PropertyDAO")
    PropertyDAO propertyDao;


    public Category findCategoryByName(CategoryName categoryName) throws DBException {



        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Category.class);
        Category category = (Category)criteria.add(Restrictions.eq("categoryName", categoryName))
                .uniqueResult();


        return category;
    }

    public Category findCategoryByStringName(String categoryName) throws DBException {


        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Category.class);
        Category category = (Category)criteria.add(Restrictions.eq("categoryName", categoryName))
                .uniqueResult();
        return category;
    }



}
