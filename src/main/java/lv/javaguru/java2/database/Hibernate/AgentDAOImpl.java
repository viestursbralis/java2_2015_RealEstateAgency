package lv.javaguru.java2.database.Hibernate;

import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;

import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.SizeOrder;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;



@Repository("ORM_AgentDAO")
@Transactional
public class AgentDAOImpl implements AgentDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void createNewAgentInDatabase(Agent agent) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(agent);
    }

    @Override
    public Agent getAgentById(Long agentId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Agent agent = (Agent) session.get(Agent.class, agentId);
        Hibernate.initialize(agent.getAgentUsers());
        return agent;

    }

    @Override
    public void deleteAgent(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Agent agent = (Agent) session.get(Agent.class, id);
        session.delete(agent);
    }

    @Override
    public void updateAgent(Agent agent) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.update(agent);
    }

    @Override
    public List<Agent> getAllAgents() throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Agent.class).list();
    }

    @Override
    public Agent findAgentByCredentials(String username, String password) throws DBException {
        Agent agent;
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Agent.class);
            criteria.add(Restrictions.eq("agentEmail", username));
            criteria.add(Restrictions.eq("agentPassword", password));
            agent = (Agent) criteria.uniqueResult();
            Hibernate.initialize(agent.getAgentUsers());
        }catch(NullPointerException e) {
            return null;
        }
        return agent;

    }



    @Override
    public Agent findLessBusyAgent() throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Agent.class, "a");
        criteria.createAlias("a.agentUsers", "agentUsers");
        criteria.addOrder(SizeOrder.asc("agentUsers"));


        return (Agent)criteria.setMaxResults(1).uniqueResult();

    }


}


