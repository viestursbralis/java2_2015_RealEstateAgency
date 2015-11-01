package lv.javaguru.java2.database;

/**
 * Created by Viesturs on 10/17/2015.
 */
import java.util.List;
import java.util.Optional;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.*;

public interface PropertyDAO {
    Long create(Property property) throws DBException;
    List<Property>findAllProperties() throws DBException;//defined in PropertyDAOImpl;
    List<Property> findPropertyById(List<Property> properties, Long propertyId) throws DBException;
    Property findPropertyById( Long propertyId) throws DBException;
    //List<Property> findPropertyById(Long propertyId) throws DBException;
    //Property findPropertyByOneId (List<Property> properties, Long propertyId) throws DBException;
    List<Property> findPropertyByAnyKeyword(String keyWord) throws DBException;
    //List<Property>findPropertyByCategoryId(List<Property> properties, Long categoryId ) throws DBException;//defined in PropertyDAOImpl;
    List<Property>findPropertyByCategoryName (List<Property> properties, CategoryName categoryName) throws DBException;
    List<Property>findPropertyByPriceRange(List<Property> properties, Double minPrice, Double maxPrice) throws DBException;
    List<Property> findPropertyByCategoryId(List<Property> properties, Long categoryId ) throws DBException;
    List<Property>findPropertiesWithCertainUtilities (List<Property> properties, Long[] utilities)throws DBException;
    List<Property>findPropertiesWithCertainUtilities (Long[] utilities)throws DBException;
    List<Property>findPropertiesWithCertainUtilities (List<Property> properties, List<Utility> utilities)throws DBException;
    List<Property>findPropertyByAgentKeyWord(List<Property> properties, String agentName) throws DBException;
    List<Property>findPropertyByAgent(List<Property> properties, Agent agent) throws DBException;
    List<Property> findPropertyByOwnerKeyWord(String ownerName)throws DBException;
    List<Property> findPropertyByOwner(List<Property>properties, PropertyOwner propertyOwner) throws DBException;
   List<Property> findPropertyByClient(List<Property>properties, User client) throws DBException;
    List<Property> findPropertyByClient(List<Property>properties, Long clientId) throws DBException;
    List<Property> findPropertyByClient(User client) throws DBException;
    List<Property> findPropertyByClientId( Long clientId) throws DBException;
    List<Agent>findAllAgents() throws DBException;
    List<Agent>findAllActiveAgents(List<Property>properties) throws DBException;



    void insert(Property property) throws DBException;
    void update(Property property);
    void delete(Long propertyId) throws DBException;
}
