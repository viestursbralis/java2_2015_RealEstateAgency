package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Category;
import lv.javaguru.java2.domain.CategoryName;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Viesturs on 10/24/2015.
 */
@Repository ("JDBC_CategoryDAO")
public class CategoryDAOImpl extends DAOImpl implements CategoryDAO {

    public Category findCategoryByName(CategoryName categoryName) throws DBException {

        if (categoryName == null ) {
            throw new NullPointerException();
        }

        Connection connection = null;
        Category category = new Category();
String catName = categoryName.toString();
        try {
            connection = getConnection();

String sql = "select * from category where CATEGORY_SHORT_NAME like '%" +catName +"%' ";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);


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

    public Category findCategoryByStringName(String categoryName) throws DBException {

        if (categoryName == null ) {
            throw new NullPointerException();
        }
        Category category = new Category();
        Connection connection = null;

        try {
            connection = getConnection();
            Statement st0 = connection.createStatement();
            String sql = "select * from category where CATEGORY_SHORT_NAME ='"+categoryName+"'";

            ResultSet resultSet=st0.executeQuery(sql);
            //ResultSet resultSet = preparedStatement.executeQuery();
            CategoryName catName = CategoryName.valueOf(categoryName);
            category.setCategoryName(catName);
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
