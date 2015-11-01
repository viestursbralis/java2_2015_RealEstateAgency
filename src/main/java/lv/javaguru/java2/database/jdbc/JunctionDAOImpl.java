package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.JunctionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by Viesturs on 10/24/2015.
 */
public class JunctionDAOImpl extends DAOImpl implements JunctionDAO {

    @Override
    public void propertyOwnerJunction(Long propertyId, List<Long> ownersId) throws DBException{
        if (propertyId == null || ownersId==null) {
            throw new NullPointerException();
        }

        Connection connection = null;


        try {
            connection = getConnection();

            for (Long ownerId : ownersId) {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("insert into property_owner_junction values (?, ?)");
                preparedStatement.setLong(1, propertyId);
                preparedStatement.setLong(2, ownerId);




                preparedStatement.executeUpdate();



            }
        } catch (Throwable e) {
            System.out.println("Exception while execute JunctionDAOImpl.propertyOwnerJunction()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }




     @Override
    public void propertyUtilitiesJunction(Long propertyId, List<Long> utilitiesId)throws DBException{
         if (propertyId == null || utilitiesId==null) {
             throw new NullPointerException();
         }

         Connection connection = null;


         try {
             connection = getConnection();

             for (Long utilityId : utilitiesId) {
                 PreparedStatement preparedStatement =
                         connection.prepareStatement("insert into property_utility_junction values (?, ?)");
                 preparedStatement.setLong(1, propertyId);
                 preparedStatement.setLong(2, utilityId);
                 preparedStatement.executeUpdate();



             }
         } catch (Throwable e) {
             System.out.println("Exception while execute JunctionDAOImpl.propertyUtilitiesJunction()");
             e.printStackTrace();
             throw new DBException(e);
         } finally {
             closeConnection(connection);
         }

     }



}
