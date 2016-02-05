package lv.javaguru.java2.database.jdbc;

/**
 * Created by Viesturs on 10/17/2015.
 */


import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository ("JDBC_PropertyDAO")

public class PropertyDAOImpl extends DAOImpl implements PropertyDAO {
    @Autowired
    @Qualifier("JDBC_AgentDAO")
    AgentDAO agentDao;
    @Autowired@Qualifier("JDBC_UserDAO")
    UserDAO userDao;
/*************************************************************************************************************/
public Long createProperty(Property property) throws DBException {
    if (property == null) {
        throw new NullPointerException();
    }

    Connection connection = null;
    Long lastInsertedPropertyID=null;
    try {
        connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into property values (default, ?, ?, ?, ?, ?,?,?,?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, property.getCategory().getCategoryId());

        preparedStatement.setLong(2, property.getClient().getUserId() );
        preparedStatement.setString(3, property.getPropertyDescription());
        preparedStatement.setDouble(4, property.getPrice());
        preparedStatement.setString(5, property.getAdress());
        preparedStatement.setLong(6, property.getArea());
        preparedStatement.setInt(7, property.getCountOfBedrooms());
        preparedStatement.setLong(8, property.getLandArea());



        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()){
            lastInsertedPropertyID=rs.getLong(1);
            property.setPropertyId(lastInsertedPropertyID);

        }
    } catch (Throwable e) {
        System.out.println("Exception while execute PropertyDAOImpl.create()");
        e.printStackTrace();
        throw new DBException(e);
    } finally {
        closeConnection(connection);
    }
return lastInsertedPropertyID;
}



    /***********************************************************************************************/
    public List<Property> findAllProperties() throws DBException {
        List<Property> propertyList = new ArrayList<>();

       String sql1="select * from property ";

        Connection connection = null;

        try {
            connection = getConnection();
            Statement st0 = connection.createStatement();
            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            Statement stAgent = connection.createStatement();

            ResultSet resultSet1=st1.executeQuery(sql1);

            while (resultSet1.next()) {
                Property property = new Property();
                Agent agent =new Agent();
                User user = new User();
                List<PropertyOwner> propertyOwners=new ArrayList<>();
                List<Utility>propertyUtilities=new ArrayList<>();
                Utility utility = new Utility ();
                property.setPropertyId(resultSet1.getLong("PROPERTY_ID"));
                property.setPropertyDescription(resultSet1.getString("PROPERTY_DESCRIPTION"));
                Long categoryId=resultSet1.getLong("CATEGORY_ID");
                String sqlCat="select c.* from category AS c where CATEGORY_ID="+categoryId;
                ResultSet resultSetCat=st0.executeQuery(sqlCat);
                        while(resultSetCat.next()){Category category = new Category();
                        category.setCategoryId(resultSetCat.getLong("CATEGORY_ID"));
                        category.setCategoryName(CategoryName.valueOf(resultSetCat.getString("CATEGORY_SHORT_NAME")));
                        category.setCategoryDescription(resultSetCat.getString("CATEGORY_DESCRIPTION"));
                            property.setCategory(category);
                }

                Long userId=resultSet1.getLong("USER_ID");
                user= userDao.getUserById(userId);
                    property.setClient(user);
                property.setPrice(resultSet1.getDouble("PRICE"));
                property.setAdress(resultSet1.getString("ADRESS"));
                property.setArea(resultSet1.getLong("AREA"));
                property.setCountOfBedrooms(resultSet1.getInt("COUNT_OF_BEDROOMS"));
                property.setLandArea(resultSet1.getLong("LAND_AREA"));
                Long propertyId=resultSet1.getLong("PROPERTY_ID");
                String sql2="select po.* from property_owner_junction AS poj " +
                        "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
                        " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;
                        ResultSet resultSet2=st2.executeQuery(sql2);

                                while(resultSet2.next()){
                                    PropertyOwner owner = new PropertyOwner();
                                    owner.setFirstName(resultSet2.getString("FIRST_NAME"));
                                    propertyOwners.add(owner);
                                }
                property.setPropertyOwners(propertyOwners);

                String sql3="select u.* from property_utility_junction AS puj " +
                        "inner join utility AS u on puj.UTILITY_ID = " +
                        " u.UTILITY_ID where puj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet3=st3.executeQuery(sql3);
                                while(resultSet3.next()){
                                    Utility propertyUtility = new Utility();
                                    propertyUtility.setUtilityId(resultSet3.getLong("UTILITY_ID"));
                                    propertyUtility.setUtilityDescription(resultSet3.getString("UTILITY_DESCRIPTION"));
                                    propertyUtilities.add(propertyUtility);
                                }
                property.setPropertyUtilities(propertyUtilities);


                propertyList.add(property);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return propertyList;
    }

  /************************************************************************************************/
  public Property findPropertyById( Long propertyId) throws DBException {
      Property propertyById = new Property();
      String sql1="select * from property where PROPERTY_ID = '"+propertyId +" '";

      Connection connection = null;

      try {
          connection = getConnection();
          Statement st0 = connection.createStatement();
          Statement st1 = connection.createStatement();
          Statement st2 = connection.createStatement();
          Statement st3 = connection.createStatement();
          Statement st4 = connection.createStatement();
          Statement stAgent = connection.createStatement();

          ResultSet resultSet1=st1.executeQuery(sql1);

          while (resultSet1.next()) {

              Agent agent =new Agent();
              User user = new User();
              List<PropertyOwner> propertyOwners=new ArrayList<>();
              List<Utility>propertyUtilities=new ArrayList<>();
              Utility utility = new Utility ();
              propertyById.setPropertyId(resultSet1.getLong("PROPERTY_ID"));



              propertyById.setPropertyDescription(resultSet1.getString("PROPERTY_DESCRIPTION"));
              Long categoryId=resultSet1.getLong("CATEGORY_ID");
              String sqlCat="select c.* from category AS c where CATEGORY_ID="+categoryId;
              ResultSet resultSetCat=st0.executeQuery(sqlCat);
              while(resultSetCat.next()){Category category = new Category();
                  category.setCategoryId(resultSetCat.getLong("CATEGORY_ID"));
                  category.setCategoryName(CategoryName.valueOf(resultSetCat.getString("CATEGORY_SHORT_NAME")));
                  category.setCategoryDescription(resultSetCat.getString("CATEGORY_DESCRIPTION"));
                  propertyById.setCategory(category);
              }

              Long userId=resultSet1.getLong("USER_ID");
              user= userDao.getUserById(userId);
              propertyById.setClient(user);
              propertyById.setPrice(resultSet1.getDouble("PRICE"));
              propertyById.setAdress(resultSet1.getString("ADRESS"));
              propertyById.setArea(resultSet1.getLong("AREA"));
              propertyById.setCountOfBedrooms(resultSet1.getInt("COUNT_OF_BEDROOMS"));
              propertyById.setLandArea(resultSet1.getLong("LAND_AREA"));

              String sql2="select po.* from property_owner_junction AS poj " +
                      "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
                      " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;
              ResultSet resultSet2=st2.executeQuery(sql2);

              while(resultSet2.next()){
                  PropertyOwner owner = new PropertyOwner();
                  owner.setFirstName(resultSet2.getString("FIRST_NAME"));
                  propertyOwners.add(owner);
              }
              propertyById.setPropertyOwners(propertyOwners);

              String sql3="select u.* from property_utility_junction AS puj " +
                      "inner join utility AS u on puj.UTILITY_ID = " +
                      " u.UTILITY_ID where puj.PROPERTY_ID = " + propertyId;
              ResultSet resultSet3=st3.executeQuery(sql3);
              while(resultSet3.next()){
                  Utility propertyUtility = new Utility();
                  propertyUtility.setUtilityId(resultSet3.getLong("UTILITY_ID"));
                  propertyUtility.setUtilityDescription(resultSet3.getString("UTILITY_DESCRIPTION"));
                  propertyUtilities.add(propertyUtility);
              }
              propertyById.setPropertyUtilities(propertyUtilities);



          }
      } catch (SQLException ex) {
          ex.printStackTrace();
      } finally {
          closeConnection(connection);
      }
      return propertyById;


  }

    /****************************************************************************************************/

    public List<Property>findPropertyByPriceRange(Double minPrice, Double maxPrice) throws DBException{
        List<Property> propertiesByPriceRange = new ArrayList<>();

        String sql1="select * from property where PRICE BETWEEN '"+minPrice+"  ' AND ' "+maxPrice+" '";

        Connection connection = null;

        try {
            connection = getConnection();
            Statement st0 = connection.createStatement();
            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            Statement st4 = connection.createStatement();
            Statement stAgent = connection.createStatement();

            ResultSet resultSet1=st1.executeQuery(sql1);

            while (resultSet1.next()) {
                Property property = new Property();
                Agent agent =new Agent();
                User user = new User();
                List<PropertyOwner> propertyOwners=new ArrayList<>();
                List<Utility>propertyUtilities=new ArrayList<>();
                Utility utility = new Utility ();
                property.setPropertyId(resultSet1.getLong("PROPERTY_ID"));
                Long propertyId = resultSet1.getLong("PROPERTY_ID");


                property.setPropertyDescription(resultSet1.getString("PROPERTY_DESCRIPTION"));
                Long categoryId=resultSet1.getLong("CATEGORY_ID");
                String sqlCat="select c.* from category AS c where CATEGORY_ID="+categoryId;
                ResultSet resultSetCat=st0.executeQuery(sqlCat);
                while(resultSetCat.next()){Category category = new Category();
                    category.setCategoryId(resultSetCat.getLong("CATEGORY_ID"));
                    category.setCategoryName(CategoryName.valueOf(resultSetCat.getString("CATEGORY_SHORT_NAME")));
                    category.setCategoryDescription(resultSetCat.getString("CATEGORY_DESCRIPTION"));
                    property.setCategory(category);
                }

                Long userId=resultSet1.getLong("USER_ID");
                user= userDao.getUserById(userId);
                property.setClient(user);
                property.setPrice(resultSet1.getDouble("PRICE"));
                property.setAdress(resultSet1.getString("ADRESS"));
                property.setArea(resultSet1.getLong("AREA"));
                property.setCountOfBedrooms(resultSet1.getInt("COUNT_OF_BEDROOMS"));
                property.setLandArea(resultSet1.getLong("LAND_AREA"));

                String sql2="select po.* from property_owner_junction AS poj " +
                        "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
                        " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet2=st2.executeQuery(sql2);

                while(resultSet2.next()){
                    PropertyOwner owner = new PropertyOwner();
                    owner.setFirstName(resultSet2.getString("FIRST_NAME"));
                    propertyOwners.add(owner);
                }
                property.setPropertyOwners(propertyOwners);

                String sql3="select u.* from property_utility_junction AS puj " +
                        "inner join utility AS u on puj.UTILITY_ID = " +
                        " u.UTILITY_ID where puj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet3=st3.executeQuery(sql3);
                while(resultSet3.next()){
                    Utility propertyUtility = new Utility();
                    propertyUtility.setUtilityId(resultSet3.getLong("UTILITY_ID"));
                    propertyUtility.setUtilityDescription(resultSet3.getString("UTILITY_DESCRIPTION"));
                    propertyUtilities.add(propertyUtility);
                }
                property.setPropertyUtilities(propertyUtilities);

                propertiesByPriceRange.add(property);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return propertiesByPriceRange;
    }

    /***********************************************************************************************/

    public List<Property> findPropertyByCategoryId(Long categoryId ) throws DBException{
        List<Property> propertyByCategory = new ArrayList<>();

        String sql1="select * from property where CATEGORY_ID = '"+categoryId +" '";

        Connection connection = null;

        try {
            connection = getConnection();
            Statement st0 = connection.createStatement();
            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();
            Statement st4 = connection.createStatement();
            Statement stAgent = connection.createStatement();

            ResultSet resultSet1=st1.executeQuery(sql1);

            while (resultSet1.next()) {
                Property property = new Property();
                Agent agent =new Agent();
                User user = new User();
                List<PropertyOwner> propertyOwners=new ArrayList<>();
                List<Utility>propertyUtilities=new ArrayList<>();
                Utility utility = new Utility ();
                property.setPropertyId(resultSet1.getLong("PROPERTY_ID"));
                Long propertyId= resultSet1.getLong("PROPERTY_ID");


                property.setPropertyDescription(resultSet1.getString("PROPERTY_DESCRIPTION"));
                //Long categoryId=resultSet1.getLong("CATEGORY_ID");
                String sqlCat="select c.* from category AS c where CATEGORY_ID="+categoryId;
                ResultSet resultSetCat=st0.executeQuery(sqlCat);
                while(resultSetCat.next()){Category category = new Category();
                    category.setCategoryId(resultSetCat.getLong("CATEGORY_ID"));
                    category.setCategoryName(CategoryName.valueOf(resultSetCat.getString("CATEGORY_SHORT_NAME")));
                    category.setCategoryDescription(resultSetCat.getString("CATEGORY_DESCRIPTION"));
                    property.setCategory(category);
                }

                Long userId=resultSet1.getLong("USER_ID");
                user= userDao.getUserById(userId);
                property.setClient(user);
                property.setPrice(resultSet1.getDouble("PRICE"));
                property.setAdress(resultSet1.getString("ADRESS"));
                property.setArea(resultSet1.getLong("AREA"));
                property.setCountOfBedrooms(resultSet1.getInt("COUNT_OF_BEDROOMS"));
                property.setLandArea(resultSet1.getLong("LAND_AREA"));

                String sql2="select po.* from property_owner_junction AS poj " +
                        "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
                        " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet2=st2.executeQuery(sql2);

                while(resultSet2.next()){
                    PropertyOwner owner = new PropertyOwner();
                    owner.setFirstName(resultSet2.getString("FIRST_NAME"));
                    propertyOwners.add(owner);
                }
                property.setPropertyOwners(propertyOwners);

                String sql3="select u.* from property_utility_junction AS puj " +
                        "inner join utility AS u on puj.UTILITY_ID = " +
                        " u.UTILITY_ID where puj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet3=st3.executeQuery(sql3);
                while(resultSet3.next()){
                    Utility propertyUtility = new Utility();
                    propertyUtility.setUtilityId(resultSet3.getLong("UTILITY_ID"));
                    propertyUtility.setUtilityDescription(resultSet3.getString("UTILITY_DESCRIPTION"));
                    propertyUtilities.add(propertyUtility);
                }
                property.setPropertyUtilities(propertyUtilities);

propertyByCategory.add(property);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

     return propertyByCategory;
    }

/*************************************************************************************************/
public List<Property> findPropertiesWithCertainUtilities (Long[] utilities)throws DBException {
    List<Property> propertyList = new ArrayList<>();
    StringBuilder sql=new StringBuilder();
    sql.append("select p.* from utility AS u INNER JOIN property_utility_junction AS puj ON puj.UTILITY_ID" +
            "=puj.UTILITY_ID INNER JOIN property AS p ON puj.PROPERTY_ID=p.PROPERTY_ID");
    if(utilities[0]==1){sql.append(" where u.UTILITY_ID=1 ");}
    if(utilities[1]==1){sql.append(" AND u.UTILITY_ID=2 ");}
    if(utilities[2]==1){sql.append(" AND u.UTILITY_ID=3 ");}
    if(utilities[3]==1){sql.append(" AND u.UTILITY_ID=4 ");}
     if(utilities[4]==1){sql.append(" AND u.UTILITY_ID=5 ");}

    Connection connection = null;
    try {

        connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql.toString());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            Property property = new Property();

            property.setPropertyId(resultSet.getLong("PROPERTY_ID"));

            propertyList.add(property);


        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeConnection(connection);
    }



    return propertyList;
}


/**************************************************************************************************/
public List<Property> findPropertiesWithCertainUtilities (List<Utility> utilities)throws DBException {
    List<Property> propertyList = new ArrayList<>();
    StringBuilder sql=new StringBuilder();
    sql.append("select p.* from utility AS u INNER JOIN property_utility_junction AS puj ON puj.UTILITY_ID" +
            "=puj.UTILITY_ID INNER JOIN property AS p ON puj.PROPERTY_ID=p.PROPERTY_ID");
    //if(utilities[0]==1){sql.append(" where u.UTILITY_ID=1 ");}
    //if(utilities[1]==1){sql.append(" AND u.UTILITY_ID=2 ");}
    //if(utilities[2]==1){sql.append(" AND u.UTILITY_ID=3 ");}
    //if(utilities[3]==1){sql.append(" AND u.UTILITY_ID=4 ");}
   // if(utilities[4]==1){sql.append(" AND u.UTILITY_ID=5 ");}

    Connection connection = null;
    try {

        connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql.toString());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            Property property = new Property();

            property.setPropertyId(resultSet.getLong("PROPERTY_ID"));

            propertyList.add(property);


        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeConnection(connection);
    }



    return propertyList;
}

/*****************************************************************************************************/
   public List<Property> findPropertyByAnyKeyword(String keyWord) throws DBException {

        List<Property> result = new ArrayList<>();


         return result;

        }


    /************************************************************************************************/


    public List<Property> findPropertyByOwnerKeyWord(String ownerName)throws DBException {
        List<Property> propertyList = new ArrayList<>();



        String sql ="select p.* from property_owner AS po INNER JOIN property_owner_junction AS poj ON poj.PROPERTY_OWNER_ID=" +
                "poj.PROPERTY_OWNER_ID INNER JOIN property AS p ON poj.PROPERTY_ID=p.PROPERTY_ID" +
                " where po.FIRST_NAME like '%"+ownerName.trim()+ "%'";




        Connection connection = null;
        try {

            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("krrrrr");
                Property property = new Property();

                property.setPropertyId(resultSet.getLong("PROPERTY_ID"));

                propertyList.add(property);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }



        return propertyList;
    }


    /*******************************************************************************************/

    public List<Property> findPropertyByOwner( PropertyOwner propertyOwner) throws DBException{
        List<Property> propertiesByOwner = new ArrayList<>();

        return propertiesByOwner;
    }

    /***********************************************************************************************/
    public List<Property> findPropertyByClient(User client) throws DBException {

        List<Property> clientProperties = new ArrayList<>();
        Long clientId=client.getUserId();
        String sql1="select * from property where USER_ID = '"+clientId +"'";

        Connection connection = null;

        try {
            connection = getConnection();
            Statement st0 = connection.createStatement();
            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();


            ResultSet resultSet1=st1.executeQuery(sql1);

            while (resultSet1.next()) {
                Property propertyByClient = new Property();


                List<PropertyOwner> propertyOwners=new ArrayList<>();
                List<Utility>propertyUtilities=new ArrayList<>();
                Utility utility = new Utility ();
                propertyByClient.setPropertyId(resultSet1.getLong("PROPERTY_ID"));
                Long propertyId=resultSet1.getLong("PROPERTY_ID");
                propertyByClient.setPropertyDescription(resultSet1.getString("PROPERTY_DESCRIPTION"));
                Long categoryId=resultSet1.getLong("CATEGORY_ID");
                String sqlCat="select c.* from category AS c where CATEGORY_ID="+categoryId;
                ResultSet resultSetCat=st0.executeQuery(sqlCat);
                while(resultSetCat.next()){Category category = new Category();
                    category.setCategoryId(resultSetCat.getLong("CATEGORY_ID"));
                    category.setCategoryName(CategoryName.valueOf(resultSetCat.getString("CATEGORY_SHORT_NAME")));
                    category.setCategoryDescription(resultSetCat.getString("CATEGORY_DESCRIPTION"));
                    propertyByClient.setCategory(category);
                }


                propertyByClient.setPrice(resultSet1.getDouble("PRICE"));
                propertyByClient.setAdress(resultSet1.getString("ADRESS"));
                propertyByClient.setArea(resultSet1.getLong("AREA"));
                propertyByClient.setCountOfBedrooms(resultSet1.getInt("COUNT_OF_BEDROOMS"));
                propertyByClient.setLandArea(resultSet1.getLong("LAND_AREA"));

                String sql2="select po.* from property_owner_junction AS poj " +
                        "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
                        " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet2=st2.executeQuery(sql2);

                while(resultSet2.next()){
                    PropertyOwner owner = new PropertyOwner();
                    owner.setFirstName(resultSet2.getString("FIRST_NAME"));
                    owner.setLastName(resultSet2.getString("LAST_NAME"));
                    owner.setOwnerEmail(resultSet2.getString("OWNER_EMAIL"));
                    owner.setOwnerPhone(resultSet2.getString("OWNER_CODE"));
                    propertyOwners.add(owner);
                }
                propertyByClient.setPropertyOwners(propertyOwners);

                String sql3="select u.* from property_utility_junction AS puj " +
                        "inner join utility AS u on puj.UTILITY_ID = " +
                        " u.UTILITY_ID where puj.PROPERTY_ID = " + propertyId;

                ResultSet resultSet3=st3.executeQuery(sql3);
                while(resultSet3.next()){
                    Utility propertyUtility = new Utility();
                    propertyUtility.setUtilityId(resultSet3.getLong("UTILITY_ID"));
                    propertyUtility.setUtilityDescription(resultSet3.getString("UTILITY_DESCRIPTION"));
                    propertyUtilities.add(propertyUtility);
                }
                propertyByClient.setPropertyUtilities(propertyUtilities);
                propertyByClient.setClient(client);
                clientProperties.add(propertyByClient);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }



        return clientProperties;

    }


    /*************************************************************************************************/
    public List<Property> findPropertyByClientId(Long clientId) throws DBException {


        List<Property> propertiesByClient = new ArrayList<>();

        String sql1="select * from property where USER_ID = '"+clientId +" '";

        Connection connection = null;

        try {
            connection = getConnection();
            Statement st0 = connection.createStatement();
            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            Statement st3 = connection.createStatement();


            ResultSet resultSet1=st1.executeQuery(sql1);

            while (resultSet1.next()) {
                Property propertyByClient = new Property();


                List<PropertyOwner> propertyOwners=new ArrayList<>();
                List<Utility>propertyUtilities=new ArrayList<>();
                Utility utility = new Utility ();
                propertyByClient.setPropertyId(resultSet1.getLong("PROPERTY_ID"));
                Long propertyId=resultSet1.getLong("PROPERTY_ID");
                propertyByClient.setPropertyDescription(resultSet1.getString("PROPERTY_DESCRIPTION"));
                Long categoryId=resultSet1.getLong("CATEGORY_ID");
                String sqlCat="select c.* from category AS c where CATEGORY_ID="+categoryId;
                ResultSet resultSetCat=st0.executeQuery(sqlCat);
                while(resultSetCat.next()){Category category = new Category();
                    category.setCategoryId(resultSetCat.getLong("CATEGORY_ID"));
                    category.setCategoryName(CategoryName.valueOf(resultSetCat.getString("CATEGORY_SHORT_NAME")));
                    category.setCategoryDescription(resultSetCat.getString("CATEGORY_DESCRIPTION"));
                    propertyByClient.setCategory(category);
                }


                propertyByClient.setPrice(resultSet1.getDouble("PRICE"));
                propertyByClient.setAdress(resultSet1.getString("ADRESS"));
                propertyByClient.setArea(resultSet1.getLong("AREA"));
                propertyByClient.setCountOfBedrooms(resultSet1.getInt("COUNT_OF_BEDROOMS"));
                propertyByClient.setLandArea(resultSet1.getLong("LAND_AREA"));

                String sql2="select po.* from property_owner_junction AS poj " +
                        "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
                        " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet2=st2.executeQuery(sql2);

                while(resultSet2.next()){
                    PropertyOwner owner = new PropertyOwner();
                    owner.setFirstName(resultSet2.getString("FIRST_NAME"));
                    propertyOwners.add(owner);
                }
                propertyByClient.setPropertyOwners(propertyOwners);

                String sql3="select u.* from property_utility_junction AS puj " +
                        "inner join utility AS u on puj.UTILITY_ID = " +
                        " u.UTILITY_ID where puj.PROPERTY_ID = " + propertyId;
                ResultSet resultSet3=st3.executeQuery(sql3);
                while(resultSet3.next()){
                    Utility propertyUtility = new Utility();
                    propertyUtility.setUtilityId(resultSet3.getLong("UTILITY_ID"));
                    propertyUtility.setUtilityDescription(resultSet3.getString("UTILITY_DESCRIPTION"));
                    propertyUtilities.add(propertyUtility);
                }
                propertyByClient.setPropertyUtilities(propertyUtilities);
               // propertyByClient.setClient(client);//in this case client is set outside this method;
                propertiesByClient.add(propertyByClient);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }




        return propertiesByClient;
    }


    /**************************************************************************************************/

    public List<Agent> findAllAgents() throws DBException {
        List<Agent> result = new ArrayList<>();
        String sql = "select * from category";
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Agent agent = new Agent();
                agent.setAgentId(resultSet.getLong("id"));
                agent.setAgentBiography(resultSet
                        .getString("agent_description"));
                result.add(agent);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    /****************************************************************************************/
    public List<Agent>findAllActiveAgents(List<Property>properties) throws DBException{
        List<Agent> allActiveAgents = new ArrayList<>();

        return allActiveAgents;
    };

    /******************************************************************************************/


    /*******************************************************************************************/


    /*******************************************************************************************/
     public void insert(Property property) throws DBException{


String sql="insert into property (CATEGORY_ID, PROPERTY_DESCRIPTION, PRICE, " +
        "ADRESS, AREA, COUNT_OF_BEDROOMS, LAND_AREA)"+"values(?,?,?,?,?,?,?)";
         Connection connection = null;
         try {

             connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);

             statement.setLong(1, property.getCategory().getCategoryId());

             statement.setString(2, property.getPropertyDescription());
             statement.setDouble(3, property.getPrice());
             statement.setString(4, property.getAdress());
             statement.setLong(5, property.getArea());
             statement.setInt(6, property.getCountOfBedrooms());
             statement.setLong(7, property.getLandArea());

             statement.executeUpdate();



         } catch (SQLException ex) {
             ex.printStackTrace();
         } finally {
             closeConnection(connection);
         }

        }
/*********************************************************************************************************/
public Long insertPhoto(String photoName) throws DBException{

        Long lastInsertedPhotoID = null;

        Connection connection = null;

    try {

        connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into photos values (default, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, photoName);



        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()){
            lastInsertedPhotoID=rs.getLong(1);
              }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeConnection(connection);
    }
return lastInsertedPhotoID;
}

/*********************************************************************************************/
public List<Photo>findAllPropertyPhotoss(Long propertyId) throws DBException {
    List<Photo>propertyPhotos=new ArrayList<>();
    String sql ="select ph.* from property_photos_junction as phj inner join photos as ph on phj.PHOTO_ID=" +
            "ph.PHOTO_ID where phj.PROPERTY_ID="+propertyId;

/*************************************************************************************************************
    String sql2="select po.* from property_owner_junction AS poj " +
            "inner join property_owner AS po on poj.PROPERTY_OWNER_ID = " +
            " po.PROPERTY_OWNER_ID where poj.PROPERTY_ID = " + propertyId;

    ****************************************************************************************/

    Connection connection = null;
    try {
        connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Photo photo = new Photo();
            photo.setPhotoId(resultSet.getLong("PHOTO_ID"));
            photo.setPhotoName(resultSet.getString("PHOTO_NAME"));
            propertyPhotos.add(photo);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeConnection(connection);
    }

   return propertyPhotos;
}
/**************************************************************/
public void insertPhoto(Photo photo) throws DBException{}

/****************************************************************************************************/
public Property findPropertyByIdWithAllLazyLists(Long propertyId) throws DBException {

    Property prop = new Property();
    return prop;
}
/******************************************************************************************************/

     public void update(Property property) {
         }

    public void mergeProperty(Property property) throws DBException{}
     public void delete(Long propertyId) throws DBException {

         String sql = " delete from property where PROPERTY_ID = ?";
         Connection connection = null;
         try {

             connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setLong(1, propertyId);


             statement.executeUpdate();



         } catch (SQLException ex) {
             ex.printStackTrace();
         } finally {
             closeConnection(connection);
         }

     }
    @Override
    public List<Property> filterByCriteria(double minPrice, double maxPrice, int minBedrooms, int maxBedrooms,
                                    Long minLandArea, Long maxLandArea, Long minArea, Long maxArea,
                                    List<Utility> utilities, String address, Category category) throws DBException{

        List<Property>properties = new ArrayList<>();


    return properties;
    }

    @Override
    public List<Property>filterByCriteriaMap(Map<String, Object> searchCriteriaMap) throws DBException{
        List<Property> properties = new ArrayList<>();


        return properties;
    }

    @Override
    public List<Property> propertiesByUtilities(Map<String, Object> searchCriteriaMap) throws DBException {
        List<Property>propertyByUtilities = new ArrayList<>();





        return propertyByUtilities;
    }

    @Override
    public Property findApprovedPropertyById( Long propertyId) throws DBException{

        Property property = new Property();





        return property;

    }


     }