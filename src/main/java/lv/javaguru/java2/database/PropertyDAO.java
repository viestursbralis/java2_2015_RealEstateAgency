package lv.javaguru.java2.database;

/**
 * Created by Viesturs on 10/17/2015.
 */
import lv.javaguru.java2.domain.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PropertyDAO {
    Long createProperty(Property property) throws DBException;
    List<Property>findAllProperties() throws DBException;//defined in PropertyDAOImpl;

    Property findPropertyById( Long propertyId) throws DBException;
    Property findApprovedPropertyById( Long propertyId) throws DBException;

    List<Property> findPropertyByAnyKeyword(String keyWord) throws DBException;

    List<Property>findPropertyByPriceRange(Double minPrice, Double maxPrice) throws DBException;
    List<Property> findPropertyByCategoryId(Long categoryId ) throws DBException;

    List<Property>findPropertiesWithCertainUtilities (List<Utility> utilities)throws DBException;
    List<Property>findPropertiesWithCertainUtilities (Long[] utilities)throws DBException;



    List<Property> findPropertyByOwnerKeyWord(String ownerName)throws DBException;

    List<Property> findPropertyByClient(User client) throws DBException;
    List<Property> findPropertyByClientId( Long clientId) throws DBException;

    List<Photo>findAllPropertyPhotoss(Long propertyId) throws DBException;
    Property findPropertyByIdWithAllLazyLists(Long propertyId) throws DBException;


    Long insertPhoto(String photoName) throws DBException;
    void insertPhoto(Photo photo) throws DBException;
    void update(Property property) throws DBException;
    void mergeProperty(Property property) throws DBException;
    void delete(Long propertyId) throws DBException;
    List<Property> filterByCriteria(double minPrice, double maxPrice, int minBedrooms, int maxBedrooms,
                                    Long minLandArea, Long maxLandArea, Long minArea, Long maxArea,
                                    List<Utility> utilities, String address, Category category) throws DBException;
    List<Property>filterByCriteriaMap(Map<String, Object> searchCriteriaMap) throws DBException;

    List<Property> propertiesByUtilities(Map<String, Object> searchCriteriaMap) throws DBException;

}
