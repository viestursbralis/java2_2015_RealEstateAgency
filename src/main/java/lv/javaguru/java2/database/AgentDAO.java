package lv.javaguru.java2.database;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Agent;


import java.util.List;

/**
 * Created by Viesturs on 10/22/2015.
 */
public interface AgentDAO {
    void create(Agent agent) throws DBException;

    Agent getAgentById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Agent agent) throws DBException;

    List<Agent> getAll() throws DBException;

    Agent findAgentByCredentials(String username, String password)throws DBException;

    Long[] findLessBusyAgentID() throws DBException;

    Agent findLessBusyAgent() throws DBException;

    List<Agent> findLessBusyAgentList()throws DBException;
}
