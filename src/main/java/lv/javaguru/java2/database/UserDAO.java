package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Agent;
import lv.javaguru.java2.domain.User;

import java.util.List;

/**
 * Created by Viktor on 01/07/2014.
 */
public interface UserDAO {

    void createNewUserInDatabase(User user) throws DBException;

    User getUserById(Long id) throws DBException;

    void deleteUser(Long id) throws DBException;

    void updateUser(User user) throws DBException;

    List<User> getAllUsers() throws DBException;

    User findUserByCredentials(String username, String password)throws DBException;

    List<User> findAllUsersOfThisAgent(Agent agent) throws DBException;

    User findUserLike(String s) throws DBException;
}
