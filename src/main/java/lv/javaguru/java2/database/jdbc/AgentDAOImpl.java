package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Statuss;
import lv.javaguru.java2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viesturs on 10/22/2015.
 */
public class AgentDAOImpl  extends DAOImpl implements AgentDAO {


        @Override
        public void create(Agent agent) throws DBException {
            if (agent == null) {
                return;
            }

            Connection connection = null;

            try {
                connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement("insert into USERS values (default, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
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

            try {
                connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("select * from AGENT where AGENT_ID = ?");
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                Agent agent = null;
                if (resultSet.next()) {
                    agent = new Agent();
                    agent.setAgentId(resultSet.getLong("AGENT_ID"));
                    agent.setAgentFirstName(resultSet.getString("AGENT_FIRST_NAME"));
                    agent.setAgentLastName(resultSet.getString("AGENT_LAST_NAME"));
                    agent.setAgentBiography(resultSet.getString("AGENT_BIOGRAPHY"));
                    //agent.setAgentStatuss(resultSet.getString("AGENT_STATUSS"));
                    agent.setAgentStatuss(Statuss.valueOf(resultSet.getString("AGENT_STATUSS")));
                    agent.setAgentPassword(resultSet.getString("AGENT_PASSWORD"));



                }
                return agent;
            } catch (Throwable e) {
                System.out.println("Exception while execute UserDAOImpl.getById()");
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }
        }

        public List<Agent> getAll() throws DBException {
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
        public void delete(Long id) throws DBException {
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
        public void update(Agent agent) throws DBException {
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
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {

                    agent.setAgentId(resultSet.getLong("AGENT_ID"));
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




