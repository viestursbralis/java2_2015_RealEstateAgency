package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyOwnerDAO;
import lv.javaguru.java2.domain.Property;
import lv.javaguru.java2.domain.PropertyOwner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Viesturs on 10/23/2015.
 */
public class PropertyOwnerDAOImpl extends DAOImpl implements PropertyOwnerDAO {
    public Long create(PropertyOwner propertyOwner) throws DBException {
        if (propertyOwner == null) {
            //return;
            throw new NullPointerException();
        }

        Connection connection = null;
        Long lastInsertedPropertyOwnerID=null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into property_owner values (default, ?, ?, ?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, propertyOwner.getFirstName());
            preparedStatement.setString(2, propertyOwner.getLastName());
            preparedStatement.setString(3, propertyOwner.getOwnerEmail());
            preparedStatement.setString(4, propertyOwner.getOwnerPhone());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                lastInsertedPropertyOwnerID=rs.getLong(1);
                propertyOwner.setId(lastInsertedPropertyOwnerID);

            }
        } catch (Throwable e) {
            System.out.println("Exception while execute PropertyDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
return lastInsertedPropertyOwnerID;
    }


}
