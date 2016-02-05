package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Statuss;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viesturs on 10/22/2015.
 */
@Repository ("JDBC_AgentDAO")
public class AgentDAOImpl  extends DAOImpl implements AgentDAO {
    @Autowired
    @Qualifier("JDBC_UserDAO")
        UserDAO userDao;

        @Override
        public void createNewAgentInDatabase(Agent agent) throws DBException {
            if (agent == null) {
                return;
            }

            Connection connection = null;

            try {
                connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement("insert into agent values (default, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, agent.getAgentFirstName());
                preparedStatement.setString(2, agent.getAgentLastName());
                preparedStatement.setString(3, agent.getAgentStatuss().name());
                preparedStatement.setString(4, agent.getAgentPassword());

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()){
                    agent.setAgentId(rs.getLong(1));
                }
            } catch (Throwable e) {
                System.out.println("Exception while execute UserDAOImpl.create()");
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }

        }

        @Override
        public Agent getAgentById(Long id) throws DBException {
            Connection connection = null;
            List<User>users = new ArrayList<>();
            try {
                connection = getConnection();
                Statement st0 = connection.createStatement();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("select * from AGENT where AGENT_ID = ?");
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                Agent agent = null;
                if (resultSet.next()) {
                    agent = new Agent();
                    agent.setAgentId(resultSet.getLong("AGENT_ID"));
                    Long agentId=resultSet.getLong("AGENT_ID");
                    agent.setAgentFirstName(resultSet.getString("AGENT_FIRST_NAME"));
                    agent.setAgentLastName(resultSet.getString("AGENT_LAST_NAME"));
                    agent.setAgentBiography(resultSet.getString("AGENT_BIOGRAPHY"));
                    //agent.setAgentStatuss(resultSet.getString("AGENT_STATUSS"));
                    agent.setAgentStatuss(Statuss.valueOf(resultSet.getString("AGENT_STATUSS")));
                    agent.setAgentPassword(resultSet.getString("AGENT_PASSWORD"));
                    String sql2="select * from users where AGENT_ID =" + agentId;
                    ResultSet resultSet2=st0.executeQuery(sql2);

                    while(resultSet2.next()){
                        Long userId = resultSet2.getLong("USER_ID");
                        User user = userDao.getUserById(userId);
                        users.add(user);

                    }
                    agent.setAgentUsers(users);


                }
                return agent;
            } catch (Throwable e) {
                System.out.println("Exception while execute AgentDAOImpl.getAgentById()");
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }
        }

        public List<Agent> getAllAgents() throws DBException {
            List<Agent> agents = new ArrayList<>();
            Connection connection = null;
            try {
                connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from AGENT");

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Agent agent = new Agent();
                    agent.setAgentId(resultSet.getLong("AGENT_ID"));
                    agent.setAgentFirstName(resultSet.getString("AGENT_FIRST_NAME"));
                    agent.setAgentLastName(resultSet.getString("AGENT_LAST_NAME"));
                    agent.setAgentBiography(resultSet.getString("AGENT_BIOGRAPHY"));

                    agent.setAgentStatuss(Statuss.valueOf(resultSet.getString("AGENT_STATUSS")));
                    agent.setAgentPassword(resultSet.getString("AGENT_PASSWORD"));
                    agents.add(agent);
                }
            } catch (Throwable e) {
                System.out.println("Exception while getting customer list UserDAOImpl.getList()");
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }
            return agents;
        }

        @Override
        public void deleteAgent(Long id) throws DBException {
            Connection connection = null;
            try {
                connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("delete from AGENT where AGENT_ID = ?");
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (Throwable e) {
                System.out.println("Exception while execute UserDAOImpl.delete()");
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }
        }

        @Override
        public void updateAgent(Agent agent) throws DBException {
            if (agent == null) {
                return;
            }

            Connection connection = null;
            try {
                connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("update AGENT set AGENT_FIRST_NAME = ?, AGENT_LAST_NAME = ?, AGENT_BIOGRAPHY=?, " +
                                " AGENT_STATUSS=?, AGENT_PASSWORD=?" +
                                "where AGENT_ID = ?");
                preparedStatement.setString(1, agent.getAgentFirstName());
                preparedStatement.setString(2, agent.getAgentLastName());
                preparedStatement.setString(3, agent.getAgentBiography());
                preparedStatement.setString(4, agent.getAgentStatuss().name());
                preparedStatement.setString(5, agent.getAgentPassword());
                preparedStatement.setLong(6, agent.getAgentId());
                preparedStatement.executeUpdate();
            } catch (Throwable e) {
                System.out.println("Exception while execute UserDAOImpl.update()");
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }
        }
        /*************************************************************************************************/
        public Agent findAgentByCredentials(String username, String password)throws DBException {
            Agent agent= new Agent();


            String sql ="select * from AGENT where AGENT_EMAIL like '%"+username.trim()+
                    "%' AND AGENT_PASSWORD like '%"+password.trim()+ "%'";

            Connection connection = null;
            try {

                connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                Statement st1 = connection.createStatement();
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {

                    agent.setAgentId(resultSet.getLong("AGENT_ID"));
                    Long agentId = resultSet.getLong("AGENT_ID");

                    agent.setAgentFirstName(resultSet.getString("AGENT_FIRST_NAME"));
                    agent.setAgentLastName(resultSet.getString("AGENT_LAST_NAME"));
                    agent.setAgentStatuss(Statuss.valueOf(resultSet.getString("AGENT_STATUSS")));
                    agent.setAgentBiography(resultSet.getString("AGENT_BIOGRAPHY"));
                    agent.setAgentEmail(resultSet.getString("AGENT_EMAIL"));
                    agent.setAgentPassword(resultSet.getString("AGENT_PASSWORD"));

                }



            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                closeConnection(connection);
            }



            return agent;
        }

        /**********************************************************************************************/
        public Long[] findLessBusyAgentID()throws DBException {
            Long[] agentIndex = new Long[2];
            // String sql ="SELECT AGENT_ID, COUNT(*) AS count FROM property GROUP BY AGENT_ID ORDER BY count ASC LIMIT 1";
//returning an agent ID with lowest property score;

        /*String sql = "SELECT agent.AGENT_ID, (SELECT count(*) FROM property WHERE property.AGENT_ID=agent.AGENT_ID)" +
                "as appears FROM agent ORDER BY appears ASC LIMIT 1";*/
            String sql = "SELECT agent.AGENT_ID, (SELECT count(*) FROM users WHERE users.AGENT_ID=agent.AGENT_ID)" +
                    "as appears FROM agent ORDER BY appears ASC LIMIT 1";

            Connection connection = null;
            try {

                connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {

                    agentIndex[0] = resultSet.getLong("AGENT_ID");

                    agentIndex[1] = resultSet.getLong("appears");


                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                closeConnection(connection);
            }


            return agentIndex;
        }

    /*******************************************************************************************/
    public Agent findLessBusyAgent()throws DBException {
        Agent agent = new Agent();


        /*String sql = "SELECT agent.AGENT_ID, (SELECT count(*) FROM property WHERE property.AGENT_ID=agent.AGENT_ID)" +
                "as appears FROM agent ORDER BY appears ASC LIMIT 1";*/

        String sql = "SELECT agent.AGENT_ID, (SELECT count(*) FROM users WHERE users.AGENT_ID=agent.AGENT_ID)" +
                "as appears FROM agent ORDER BY appears ASC LIMIT 1";

        Connection connection = null;
        try {

            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            Long agentId=null;
            while (resultSet.next()) {

                agentId = resultSet.getLong("AGENT_ID");
            }
            agent=getAgentById(agentId);



        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }


        return agent;
    }

/**********************************************************************************************************/

public List<Agent> findLessBusyAgentList()throws DBException {
    Long resultset = null;
    List<Agent> agentList = new ArrayList<>();
    // String sql ="SELECT AGENT_ID, COUNT(*) AS count FROM property GROUP BY AGENT_ID ORDER BY count ASC LIMIT 1";
//returning an agent ID with lowest property score;

    String sql ="SELECT agent.AGENT_ID, (SELECT count(*) FROM property WHERE property.AGENT_ID=agent.AGENT_ID)" +
            "as appears FROM agent";
    Connection connection = null;
    try {

        connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Agent agent= new Agent();
            //resultset=resultSet.getLong("AGENT_ID");

            agent.setAgentId(resultSet.getLong("appears"));

            agentList.add(agent);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeConnection(connection);
    }



    return agentList;
}
/************************************************************************************************************/

    }




