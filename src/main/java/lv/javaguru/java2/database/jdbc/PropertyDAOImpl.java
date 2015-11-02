package lv.javaguru.java2.database.jdbc;

/**
 * Created by Viesturs on 10/17/2015.
 */


import lv.javaguru.java2.database.AgentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.*;

import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PropertyDAOImpl extends DAOImpl implements PropertyDAO {
AgentDAO agentDao=new AgentDAOImpl();
    UserDAO userDao= new UserDAOImpl();
/*************************************************************************************************************/
public Long create(Property property) throws DBException {
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
      String sql1="select * from property where PROPERY_ID = '"+propertyId +" '";

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


/********************************************************************************************/
public List<Property> findPropertyById(List<Property> properties, Long propertyId) throws DBException {
    List<Property> propertyById= new ArrayList();

    propertyById=properties.stream()
            .filter((Property p) -> p.getPropertyId() == propertyId)
            .collect(Collectors.toList());

    return propertyById;
}


    /***********************************************************************************************/
    public Property findPropertyByOneId(List<Property> properties, Long propertyId) throws DBException {
        Property propertyById= new Property();

        propertyById=(Property)properties.stream()
                .filter((Property p) -> p.getPropertyId() == propertyId);

        return propertyById;
    }


    /***************************************************************************************************/

/*public List<Property> findPropertyByCategoryId(List<Property> properties, Long categoryId ) throws DBException {
    List<Property> propertiesByCategoryId =new ArrayList<>();

    Optional<Property> price=properties.stream().min(Comparator.comparingDouble(Property::getPrice));


    return propertiesByCategoryId;

}*/
/************************************************************************************************/
public List<Property> findPropertyByCategoryId(List<Property> properties, Long categoryId ) throws DBException {
    List<Property> propertiesByCategoryId =new ArrayList<>();

    propertiesByCategoryId=properties
            .stream()
            .filter((Property p) -> p.getCategory().getCategoryId()==categoryId)
            .collect(Collectors.toList());


    return propertiesByCategoryId;

}

/**********************************************************************************************/
public List<Property>findPropertyByCategoryName (List<Property> properties, CategoryName categoryName) throws DBException {
    List<Property> propertiesByCategoryName =new ArrayList<>();


    return propertiesByCategoryName;
}


/***************************************************************************************************/
public List<Property>findPropertyByPriceRange(List<Property> properties, Double minPrice, Double maxPrice) throws DBException{
    List<Property> propertiesByPriceRange=new ArrayList<>();


    propertiesByPriceRange=properties
            .stream()
            .filter((Property p) -> p.getPrice() > minPrice && p.getPrice() < maxPrice)
            .collect(Collectors.toList());


    return propertiesByPriceRange;
}

  /***************************************************************************************************/
  public List<Property> findPropertiesWithCertainUtilities (List<Property> properties, Long [] utilities)throws DBException {
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
public List<Property> findPropertiesWithCertainUtilities (Long [] utilities)throws DBException {
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

  /*************************************************************************************************/
  public List<Property>findPropertiesWithCertainUtilities (List<Property> properties, List<Utility> utilities)throws DBException {
      List<Property> propertiesByUtilities = new ArrayList<>();

      propertiesByUtilities=properties
              .stream()
              .filter((Property p) -> p.getPropertyUtilities().equals(utilities))
              .collect(Collectors.toList());


   return propertiesByUtilities;
  }


/*****************************************************************************************************/
   public List<Property> findPropertyByAnyKeyword(String keyWord) throws DBException {

        List<Property> result = new ArrayList<>();


         return result;

        }


    /************************************************************************************************/



    public List<Property> findPropertyByAgentKeyWord(List<Property> properties, String agentName)throws DBException {

        List<Property> propertyList = new ArrayList<>();
        List<PropertyOwner> ownerList = new ArrayList<>();

       String sql ="select p.* from property_owner AS po INNER JOIN property_owner_junction AS poj ON poj.PROPERTY_OWNER_ID=" +
               "poj.PROPERTY_OWNER_ID INNER JOIN property AS p ON pos.PROPERTY_ID=p.PROPERTY_ID" +
               " where (po.PROPERTY_OWNER_ID=";

        Connection connection = null;
        try {

            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Property property = new Property();
                PropertyOwner propertyOwner = new PropertyOwner();
                property.setPropertyId(resultSet.getLong("PROPERTY_ID"));

                propertyOwner.setFirstName(resultSet.getString("FIRST_NAME"));
                propertyOwner.setLastName(resultSet.getString("LAST_NAME"));

                ownerList.add(propertyOwner);
                property.setPropertyOwners(ownerList);
                propertyList.add(property);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return propertyList;

    }
    /********************************************************************************************/
    public List<Property>findPropertyByAgent(List<Property> properties, Agent agent) throws DBException{
        List<Property> propertyListByAgents=new ArrayList<>();



        return propertyListByAgents;
    }

    /*********************************************************************************************/

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

    public List<Property> findPropertyByOwner(List<Property>properties, PropertyOwner propertyOwner) throws DBException{
        List<Property> propertiesByOwner = new ArrayList<>();

        return propertiesByOwner;
    }


    /*******************************************************************************************/
   public  List<Property> findPropertyByClient(List<Property>properties, User client) throws DBException {
       List<Property> propertiesByClient=new ArrayList<>();

       propertiesByClient=properties
               .stream()

               .filter((Property p) -> p.getPropertyId()==1)
               .collect(Collectors.toList());

       for(Property prop:propertiesByClient){
           System.out.println(prop);
           System.out.println("This sucks inside a props!!!");
       }
       System.out.println(propertiesByClient);
       System.out.println("This sucks outside a props!!!");
       return propertiesByClient;
   }

    /***********************************************************************************************/

    public  List<Property> findPropertyByClient(List<Property>properties, Long clientId) throws DBException {
        List<Property> propertiesByClient=new ArrayList<>();

        propertiesByClient=properties
                .stream()

                .filter((Property p) -> p.getPropertyId()==clientId)
                .collect(Collectors.toList());


        return propertiesByClient;
    }

    /***********************************************************************************************/
    public List<Property> findPropertyByClient(User client) throws DBException {

        List<Property> clientProperties = new ArrayList<>();
        Long clientId=client.getUserId();
        String sql1="select * from property where USER_ID = '"+clientId +" '";

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
                Property propertyByClient = new Property();
                Agent agent =new Agent();

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
    public List<Property> findPropertyByClientId( Long clientId) throws DBException {
        List<Property> propertiesByClientId= new ArrayList<>();





        return propertiesByClientId;
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


     public void update(Property property) {
         }
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
     }