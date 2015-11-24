package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.Statuss;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl extends DAOImpl implements UserDAO {
@Autowired
    AgentDAO agentDAO;


    @Override
    public void create(User user) throws DBException {
        if (user == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into USERS values (default, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getStatuss().name());
            preparedStatement.setString(4, user.getUserEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setLong(6, user.getAgent().getAgentId());



            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                user.setUserId(rs.getLong(1));
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
    public User getUserById(Long id) throws DBException {
        Connection connection = null;
            User user=new User();
        List<Property> properties=new ArrayList<>();
        try {
            connection = getConnection();
            Statement st2 = connection.createStatement();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from USERS where USER_ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();



            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getLong("USER_ID"));
                user.setFirstName(resultSet.getString("USER_FIRST_NAME"));
                user.setLastName(resultSet.getString("USER_LAST_NAME"));
                user.setLastName(resultSet.getString("USER_PASSWORD"));
                user.setLastName(resultSet.getString("USER_EMAIL"));
                user.setStatuss(Statuss.valueOf(resultSet.getString("USER_STATUSS")));
                Long agentId = (resultSet.getLong("AGENT_ID"));
                String sql2="select a.* from agent AS a where a.AGENT_ID =" + agentId;
                ResultSet resultSet2=st2.executeQuery(sql2);

                while(resultSet2.next()){
                    Agent agent = new Agent();
                    agent.setAgentFirstName(resultSet2.getString("AGENT_FIRST_NAME"));
                    agent.setAgentLastName(resultSet2.getString("AGENT_LAST_NAME"));
                    agent.setAgentStatuss(Statuss.valueOf(resultSet2.getString("AGENT_STATUSS")));
                    agent.setAgentBiography(resultSet2.getString("AGENT_BIOGRAPHY"));
                    agent.setAgentEmail(resultSet2.getString("AGENT_EMAIL"));
                    agent.setAgentPassword(resultSet2.getString("AGENT_PASSWORD"));
                    user.setAgent(agent);
                }

                }

        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return user;
    }

    public List<User> getAll() throws DBException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from USERS");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("UserID"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                users.add(user);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list UserDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return users;
    }

    @Override
    public void delete(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from USERS where UserID = ?");
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
    public void update(User user) throws DBException {
        if (user == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update USERS set FirstName = ?, LastName = ? " +
                            "where UserID = ?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getUserId());
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
  public User findUserByCredentials(String username, String password)throws DBException {
      User user=new User();


      String sql ="select * from users where USER_EMAIL like '%"+username.trim()+
              "%' AND USER_PASSWORD like '%"+password.trim()+ "%'";

         Connection connection = null;
      try {

          connection = getConnection();
          Statement st0 = connection.createStatement();
          PreparedStatement statement = connection.prepareStatement(sql);
          ResultSet resultSet = statement.executeQuery();
          while (resultSet.next()) {

              user.setUserId(resultSet.getLong("USER_ID"));

              user.setFirstName(resultSet.getString("USER_FIRST_NAME"));
              user.setLastName(resultSet.getString("USER_LAST_NAME"));
              user.setStatuss(Statuss.valueOf(resultSet.getString("USER_STATUSS")));
              user.setUserEmail(resultSet.getString("USER_EMAIL"));
              user.setPassword(resultSet.getString("USER_PASSWORD"));
              Long userId=resultSet.getLong("USER_ID");


              Long agentId = resultSet.getLong("AGENT_ID");
              Agent agent = new Agent();
              agent=agentDAO.getAgentById(agentId);
              user.setAgent(agent);

                        }
      } catch (SQLException ex) {
          ex.printStackTrace();
      } finally {
          closeConnection(connection);
      }



      return user;
  }

    /**********************************************************************************************/




}
