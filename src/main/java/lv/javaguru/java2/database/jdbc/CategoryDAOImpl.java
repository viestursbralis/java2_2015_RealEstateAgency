package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UtilityDAO;
import lv.javaguru.java2.domain.Category;
import lv.javaguru.java2.domain.CategoryName;
import lv.javaguru.java2.domain.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viesturs on 10/24/2015.
 */
public class CategoryDAOImpl extends DAOImpl implements CategoryDAO {

    public Category findCategoryByName(CategoryName categoryName) throws DBException {

        if (categoryName == null ) {
            throw new NullPointerException();
        }

        Connection connection = null;
        Category category = new Category();

        try {
            connection = getConnection();

String sql = "select * from category where CATEGORY_SHORT_NAME like '%" +categoryName +"%' ";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //agent.setAgentStatuss(Statuss.valueOf(resultSet.getString("AGENT_STATUSS")));
                    ResultSet resultSet = preparedStatement.executeQuery();

            category.setCategoryName(categoryName);
                    while (resultSet.next()) {

                        category.setCategoryId(resultSet.getLong("CATEGORY_ID"));
                        category.setCategoryDescription(resultSet.getString("CATEGORY_DESCRIPTION"));
                    }


        } catch (Throwable e) {
            System.out.println("Exception while execute UtilityDAOImpl.createUtility()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

        return category;
    }

}
