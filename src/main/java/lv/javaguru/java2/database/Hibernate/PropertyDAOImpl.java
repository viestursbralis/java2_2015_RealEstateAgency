package lv.javaguru.java2.database.Hibernate;

/**
 * Created by Viesturs on 04-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("ORM_PropertyDAO")
@Transactional
public class PropertyDAOImpl implements PropertyDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long createProperty(Property property) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        //session.persist(property);
        return (Long) session.save(property);
    }

    @Override
    public List<Property>findAllProperties() throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Property.class)
                                .list();


    }


    @Override
    public Property findPropertyById(Long propertyId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Property prop = (Property) session.get(Property.class, propertyId);
        Hibernate.initialize(prop.getPropertyUtilities());
        return prop;
    }
    @Override
    public List<Property> findPropertyByAnyKeyword(String keyWord) throws DBException{
        Session session = sessionFactory.getCurrentSession();
        Criteria c2 = session.createCriteria(Property.class);
        List<Property>properties = c2.add(Restrictions.like("propertyDescription", keyWord)).list();

        return properties;
    }

    @Override
    public List<Property>findPropertyByPriceRange(Double minPrice, Double maxPrice) throws DBException {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(Property.class, "price");

        List<Property>propertiesByRange=(List<Property>)criteria.add(Restrictions.between("price", minPrice, maxPrice));
    return propertiesByRange;
    }



@Override
public List<Property> findPropertyByCategoryId(Long categoryId ) throws DBException{
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Property.class, "property");
    criteria.createAlias("property.category", "category");
    List<Property> propertiesByCategory = (List<Property>)criteria.add(Restrictions.eq("category", categoryId));
    return propertiesByCategory;
}

@Override
    public List<Property>findPropertiesWithCertainUtilities (List<Utility> utilities)throws DBException {

    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Property.class, "property");
    criteria.createAlias("property.propertyUtilities", "propertyUtilities");
    List<Property>propertiesWithUtilities = (List<Property>)criteria.add(Restrictions.eq("propertyUtilities", utilities));


    return propertiesWithUtilities;
}

    @Override
    public List<Property>findPropertiesWithCertainUtilities (Long[] utilities)throws DBException{
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class, "property");
        criteria.createAlias("property.propertyUtilities", "propertyUtilities");
        criteria.createAlias("propertyUtilities.utilityId", "utilityId");
        List<Property>propertiesWithUtilities = (List<Property>)criteria.add(Restrictions.eq("utilityId", utilities));
        return propertiesWithUtilities;
    }


    @Override//do not working yet;
    public List<Property> findPropertyByOwnerKeyWord(String ownerName)throws DBException{
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class);
        criteria.add(Restrictions.like("name","Mou%"));
        List<Property> results = criteria.list();


       return results;
    }

    @Override
    public List<Property> findPropertyByClient(User client) throws DBException{
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class);
        List<Property>propertiesByClient
                = criteria.add(Restrictions.eq("client", client)).list();

        return propertiesByClient;
    }

@Override
public List<Property> findPropertyByClientId(Long clientId) throws DBException{
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Property.class, "property");
    criteria.createAlias("property.client", "client");
    criteria.createAlias("client.userId", "userId");
    List<Property>propertiesByClient
            = criteria.add(Restrictions.eq("clientId", clientId)).list();
    return propertiesByClient;
}


    @Override
    public List<Photo>findAllPropertyPhotoss(Long propertyId) throws DBException{

        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class);

        Property property = findPropertyById(propertyId);
        return property.getPropertyPhotos();



    }

    @Override
    public List<Property> filterByCriteria(double minPrice, double maxPrice, int minBedrooms, int maxBedrooms, Long minLandArea,
                                           Long maxLandArea, Long minArea, Long maxArea, List<Utility> utilities,
                                           String address, Category category) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class, "property");

        criteria.createAlias("property.propertyUtilities", "propertyUtilities");
        criteria.createAlias("propertyUtilities.utilityId", "utilityId");
        List<Property> filteredPropertyList = (List<Property>) criteria.add(Restrictions.between("price", minPrice, maxPrice)).add(Restrictions.between("countOfBedrooms", minBedrooms, maxBedrooms))
                .add(Restrictions.between("landArea", minLandArea, maxLandArea)).add(Restrictions.between("area", minArea, maxArea))
                .add(Restrictions.eq("propertyUtilities", utilities)).add(Restrictions.like("adress", address));

        return filteredPropertyList;

    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Property property = (Property) session.get(Property.class, id);
        session.delete(property);
    }

    @Override
    public void update(Property property) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.update(property);
    }

    @Override
    public Long insertPhoto(String photoName) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Photo photo = new Photo();
        photo.setPhotoName(photoName);
        return (Long)session.save(photo);
    }



}

