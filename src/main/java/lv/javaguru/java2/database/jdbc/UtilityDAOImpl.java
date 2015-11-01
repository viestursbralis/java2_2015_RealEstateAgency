package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UtilityDAO;
import lv.javaguru.java2.domain.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viesturs on 10/24/2015.
 */
public class UtilityDAOImpl extends DAOImpl implements UtilityDAO {

    public List<Utility> createUtility(List<Long> utilityId) throws DBException {

        if (utilityId == null ) {
            throw new NullPointerException();
        }

        Connection connection = null;
        List<Utility> utilities=new ArrayList<>();

        try {
            connection = getConnection();
            Long index =new Long(1);
            for (Long utilId : utilityId) {
                    //System.out.println(index);
                if (utilId==1){

                    PreparedStatement preparedStatement =
                            connection.prepareStatement("select * from utility where UTILITY_ID = ?");
                    preparedStatement.setLong(1, index);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Utility utility=new Utility();
                    while (resultSet.next()) {

                        utility.setUtilityId(resultSet.getLong("UTILITY_ID"));
                        utility.setUtilityDescription(resultSet.getString("UTILITY_DESCRIPTION"));


                    }
                    utilities.add(utility);
                }
                index=index+1;


            }

        } catch (Throwable e) {
            System.out.println("Exception while execute UtilityDAOImpl.createUtility()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

        return utilities;
    }

}
