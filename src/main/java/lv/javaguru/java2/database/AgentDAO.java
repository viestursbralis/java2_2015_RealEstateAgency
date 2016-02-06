package lv.javaguru.java2.database;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Agent;


import java.util.List;

/**
 * Created by Viesturs on 10/22/2015.
 */
public interface AgentDAO {
    void createNewAgentInDatabase(Agent agent) throws DBException;

    Agent getAgentById(Long id) throws DBException;

    void deleteAgent(Long id) throws DBException;

    void updateAgent(Agent agent) throws DBException;

    List<Agent> getAllAgents() throws DBException;

    Agent findAgentByCredentials(String username, String password)throws DBException;



    Agent findLessBusyAgent() throws DBException;


}
