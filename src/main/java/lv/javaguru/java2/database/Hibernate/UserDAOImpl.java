package lv.javaguru.java2.database.Hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viesturs on 26-Nov-15.
 */
@Repository("ORM_UserDAO")
@Transactional
public class UserDAOImpl implements UserDAO{
@Autowired
private SessionFactory sessionFactory;


    @Override
    public void createNewUserInDatabase(User user) throws DBException {
       Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public User getUserById(Long id) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        return (User) session.get(User.class, id);

    }

    @Override
    public void deleteUser(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        session.delete(user);
    }

    @Override
    public void updateUser(User user) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public List<User> getAllUsers() throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(User.class).list();
    }

    @Override
    public User findUserByCredentials(String username, String password) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c2 = session.createCriteria(User.class);
        c2.add(Restrictions.like("userEmail", username));
        c2.add(Restrictions.like("password", password));


        return (User) c2.uniqueResult();

    }

    @Override
    public List<User> findAllUsersOfThisAgent(Agent agent) throws DBException{
        List<User>users = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        users = session.createCriteria(User.class, "u")
                .add(Restrictions.eq("u.agent", agent))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

        return users;
    }

    @Override
    public User findUserLike(String s) throws DBException{
        User user = new User();
        Session session = sessionFactory.getCurrentSession();
        Criteria c2 = session.createCriteria(User.class);
        c2.add(Restrictions.like("firstName", s));
        c2.add(Restrictions.like("lastName", s));


        return user;
    }

    @Override
    public User findUserByPropertyID(Long ID) throws DBException{//ID - property id;
        User user = new User();
        Session session = sessionFactory.getCurrentSession();
        Criteria c2 = session.createCriteria(Property.class, "property");
        c2.add(Restrictions.eq("propertyId", ID));
        c2.setProjection(Projections.property("client").as("client"));
        c2.setResultTransformer(Transformers.aliasToBean(User.class));



        return user;
    }




}
