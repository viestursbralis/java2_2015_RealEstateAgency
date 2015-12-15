package lv.javaguru.java2.database;

/**
 * Created by Viesturs on 10/17/2015.
 */
import lv.javaguru.java2.domain.*;

import java.util.List;

public interface PropertyDAO {
    Long createProperty(Property property) throws DBException;
    List<Property>findAllProperties() throws DBException;//defined in PropertyDAOImpl;
    //List<Property> findPropertyById(List<Property> properties, Long propertyId) throws DBException;
    Property findPropertyById( Long propertyId) throws DBException;
    //List<Property> findPropertyById(Long propertyId) throws DBException;
    //Property findPropertyByOneId (List<Property> properties, Long propertyId) throws DBException;
    List<Property> findPropertyByAnyKeyword(String keyWord) throws DBException;
    //List<Property>findPropertyByCategoryId(List<Property> properties, Long categoryId ) throws DBException;//defined in PropertyDAOImpl;
    //List<Property>findPropertyByCategoryName (List<Property> properties, CategoryName categoryName) throws DBException;
    List<Property>findPropertyByPriceRange(Double minPrice, Double maxPrice) throws DBException;
    List<Property> findPropertyByCategoryId(Long categoryId ) throws DBException;
    //List<Property>findPropertiesWithCertainUtilities (List<Property> properties, Long[] utilities)throws DBException;
    List<Property>findPropertiesWithCertainUtilities (List<Utility> utilities)throws DBException;
    List<Property>findPropertiesWithCertainUtilities (Long[] utilities)throws DBException;
    //List<Property>findPropertiesWithCertainUtilities (List<Property> properties, List<Utility> utilities)throws DBException;

    //List<Property>findPropertyByAgent(List<Property> properties, Agent agent) throws DBException;
    List<Property> findPropertyByOwnerKeyWord(String ownerName)throws DBException;
    //List<Property> findPropertyByOwner(List<Property>properties, PropertyOwner propertyOwner) throws DBException;
   //List<Property> findPropertyByClient(List<Property>properties, User client) throws DBException;
   // List<Property> findPropertyByClient(List<Property>properties, Long clientId) throws DBException;
    List<Property> findPropertyByClient(User client) throws DBException;
    List<Property> findPropertyByClientId( Long clientId) throws DBException;
    //List<Agent>findAllAgents() throws DBException;//todo implement;
    //List<Agent>findAllActiveAgents(List<Property>properties) throws DBException;//todo implement;
    List<Photo>findAllPropertyPhotoss(Long propertyId) throws DBException;


    //void insert(Property property) throws DBException;
    Long insertPhoto(String photoName) throws DBException;
    void update(Property property) throws DBException;
    void delete(Long propertyId) throws DBException;
}
