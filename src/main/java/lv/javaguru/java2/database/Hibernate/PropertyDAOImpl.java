package lv.javaguru.java2.database.Hibernate;

/**
 * Created by Viesturs on 04-Dec-15.
 */

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PropertyDAO;
import lv.javaguru.java2.database.UtilityDAO;
import lv.javaguru.java2.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("ORM_PropertyDAO")
@Transactional
public class  PropertyDAOImpl implements PropertyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("ORM_UtilityDAO")
    private UtilityDAO utilityDao;

    @Override
    public Long createProperty(Property property) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        //session.enableFilter("utilityTrue");
        //session.persist(property);
        return (Long) session.save(property);
    }

    @Override
    public List<Property>findAllProperties() throws DBException {
        Session session = sessionFactory.getCurrentSession();



        return session.createCriteria(Property.class)
                .add(Restrictions.eq("postStatuss", PostStatuss.APPROVED))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                                .list();


    }


    @Override
    public Property findPropertyById(Long propertyId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Property prop = (Property) session.get(Property.class, propertyId);
        Hibernate.initialize(prop.getCategory());
        Hibernate.initialize(prop.getPropertyOwners());
        Hibernate.initialize(prop.getPropertyPhotos());
        Hibernate.initialize(prop.getPropertyUtilities());
       /* List<Utility> utils = prop.getPropertyUtilities();
        System.out.println("Util list from hibernate before tweaking:");
        System.out.println(utils.toString());

        List<Utility>allUtilities = utilityDao.getAllUtilities();
        for(int i=0; i<allUtilities.size(); i++){
            Long id = allUtilities.get(i).getUtilityId();
            for(Utility u:utils){Long shortId = u.getUtilityId();
            if (id==shortId){allUtilities.get(i).setChecked(true);}

            }
        }
utils.clear();

        utils=allUtilities;


        System.out.println("Util list from hibernate after tweaking :");
        System.out.println(utils.toString());

        prop.setPropertyUtilities(utils);
        System.out.println("Property from hibernate: "+prop.toString());*/
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
        Hibernate.initialize(property.getPropertyPhotos());
        return property.getPropertyPhotos();



    }

    @Override
    public Property findPropertyByIdWithAllLazyLists(Long propertyId) throws DBException {

        Session session = sessionFactory.getCurrentSession();
        Property property = (Property) session.get(Property.class, propertyId);

        Hibernate.initialize(property.getPropertyOwners());
        Hibernate.initialize(property.getPropertyUtilities());
        Hibernate.initialize(property.getPropertyPhotos());
        return property;
    }


    /*@Override
    public void delete(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Property property = (Property) session.get(Property.class, id);
        session.delete(property);
    }*/

    @Override
    public void delete(Long ID){
        Serializable id = ID;
        Session session = sessionFactory.getCurrentSession();
        Object persistentInstance = session.load(Property.class, ID);
        if (persistentInstance != null) {
            session.delete(persistentInstance);
        }

    }

    @Override
    public void update(Property property) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(property);
    }

    @Override
    public void mergeProperty(Property property) throws DBException{
        Session session = sessionFactory.getCurrentSession();
        session.merge(property);


    }

    @Override
    public Long insertPhoto(String photoName) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Photo photo = new Photo();
        photo.setPhotoName(photoName);
        return (Long)session.save(photo);
    }

    @Override
    public void insertPhoto(Photo photo) throws DBException{
        Session session = sessionFactory.getCurrentSession();
        //session.persist(property);
        session.save(photo);

    }

    @Override
    public List<Property> filterByCriteria(double minPrice, double maxPrice, int minBedrooms,
                                           int maxBedrooms, Long minLandArea, Long maxLandArea,
                                           Long minArea, Long maxArea, List<Utility> utilities,
                                           String address, Category category) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class, "property");

        criteria.createAlias("property.propertyUtilities", "propertyUtilities");
       // criteria.createAlias("category.categoryId", "categoryId");

                if(minPrice!=0 && maxPrice !=0){
                    criteria.add(Restrictions.between("price", minPrice, maxPrice));
                }
               /* if (minPrice!=0 && maxPrice==0){
                    criteria.add(Restrictions.ge("price", minPrice));
                }
                if (minPrice==0 && maxPrice!=0){
                    criteria.add(Restrictions.le("price", maxPrice));
                }

                if(minBedrooms!=0 && maxBedrooms !=0){
                    criteria.add(Restrictions.between("countOfBedrooms", minBedrooms, maxBedrooms));
                }
                if (minBedrooms!=0 && maxBedrooms==0){
                    criteria.add(Restrictions.ge("countOfBedrooms", minBedrooms));
                }
                if (minBedrooms==0 && maxBedrooms!=0){
                    criteria.add(Restrictions.le("countOfBedrooms", maxBedrooms));
                }
                //.add(Restrictions.between("countOfBedrooms", minBedrooms, maxBedrooms))
                if(minLandArea!=0 && maxLandArea !=0){
                    criteria.add(Restrictions.between("landArea", minLandArea, maxLandArea));
                }
                if (minLandArea!=0 && maxLandArea==0){
                    criteria.add(Restrictions.ge("landArea", minLandArea));
                }
                if (minLandArea==0 && maxLandArea!=0){
                    criteria.add(Restrictions.le("landArea", maxLandArea));
                }
                //.add(Restrictions.between("landArea", minLandArea, maxLandArea))
                if(minArea!=0 && maxArea !=0){
                    criteria.add(Restrictions.between("area", minArea, maxArea));
                }
                if (minArea!=0 && maxArea==0){
                    criteria.add(Restrictions.ge("area", minArea));
                }
                if (minArea==0 && maxArea!=0){
                 criteria.add(Restrictions.le("area", maxArea));
                }

                //.add(Restrictions.between("area", minArea, maxArea))
                //.add(Restrictions.eq("propertyUtilities", utilities))
                if (!address.equals("")){
                 criteria.add(Restrictions.like("adress", address));
                }
*/
        criteria.add(Restrictions.eq("postStatuss", PostStatuss.APPROVED));
                List<Property> propertiesToShow = new ArrayList<>();
        List<Property>properties = criteria.add(Restrictions.eq("category", category))
        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();



        for (Property p: properties   ){

            Hibernate.initialize(p.getPropertyOwners());
            Hibernate.initialize(p.getPropertyPhotos());
            Hibernate.initialize(p.getPropertyUtilities());
            propertiesToShow.add(p);
        }
                 return properties;

        }


    @Override
    public List<Property>filterByCriteriaMap(Map<String, Object> searchCriteriaMap) throws DBException{


        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Property.class, "property");

        criteria.createAlias("property.propertyUtilities", "propertyUtilities");
        // criteria.createAlias("category.categoryId", "categoryId");
        Double minPrice = (Double)searchCriteriaMap.get("minPrice");
        Double maxPrice = (Double)searchCriteriaMap.get("maxPrice");
        if(minPrice!=0 && maxPrice !=0) {
            criteria.add(Restrictions.between("price", minPrice, maxPrice));
        }

        if (minPrice!=0 && maxPrice==0){
                    criteria.add(Restrictions.ge("price", minPrice));
                }
        if (minPrice==0 && maxPrice!=0){
                    criteria.add(Restrictions.le("price", maxPrice));
                }

        int minBedrooms = (Integer)searchCriteriaMap.get("minBedrooms");
        int maxBedrooms = (Integer)searchCriteriaMap.get("maxBedrooms");
                if(minBedrooms!=0 && maxBedrooms !=0){
                    criteria.add(Restrictions.between("countOfBedrooms", minBedrooms, maxBedrooms));
                }
                if (minBedrooms!=0 && maxBedrooms==0){
                    criteria.add(Restrictions.ge("countOfBedrooms", minBedrooms));
                }
                if (minBedrooms==0 && maxBedrooms!=0){
                    criteria.add(Restrictions.le("countOfBedrooms", maxBedrooms));
                }

                //.add(Restrictions.between("countOfBedrooms", minBedrooms, maxBedrooms))
        Long minLandArea = (Long)searchCriteriaMap.get("minLandArea");
        Long maxLandArea = (Long)searchCriteriaMap.get("maxLandArea");
                if(minLandArea!=0 && maxLandArea !=0){
                    criteria.add(Restrictions.between("landArea", minLandArea, maxLandArea));
                }
                if (minLandArea!=0 && maxLandArea==0){
                    criteria.add(Restrictions.ge("landArea", minLandArea));
                }
                if (minLandArea==0 && maxLandArea!=0){
                    criteria.add(Restrictions.le("landArea", maxLandArea));
                }
                //.add(Restrictions.between("landArea", minLandArea, maxLandArea))
        Long minArea = (Long)searchCriteriaMap.get("minArea");
        Long maxArea = (Long)searchCriteriaMap.get("maxArea");
                if(minArea!=0 && maxArea !=0){
                    criteria.add(Restrictions.between("area", minArea, maxArea));
                }
                if (minArea!=0 && maxArea==0){
                    criteria.add(Restrictions.ge("area", minArea));
                }
                if (minArea==0 && maxArea!=0){
                 criteria.add(Restrictions.le("area", maxArea));
                }

                //.add(Restrictions.between("area", minArea, maxArea))



        List<Utility> utilsSubset = (List<Utility>)searchCriteriaMap.get("utils");
       int size = utilsSubset.size();
        List<String>utilDescriptions = new ArrayList<>();
        List<Long>utilIds = new ArrayList<>();
        for(int i=0; i<size; i++){

            utilDescriptions.add(utilsSubset.get(i).getUtilityDescription());
            utilIds.add(utilsSubset.get(i).getUtilityId());

        }
/*
        //DetachedCriteria.forClass(Property.class).createAlias("propertyUtilities", "propUtils");
        criteria.createAlias("propertyUtilities", "propUtils");
        for(Utility u:utilsSubset){

            criteria.add(Restrictions.eq("propUtils.utilityDescription",u.getUtilityDescription() ));
        }
*/






       /* int size = utils.size();
        String[]utilDescriptions = new String[size];

        for(int i=0; i<size; i++){

            utilDescriptions[i]= utils.get(i).getUtilityDescription();


        }
        criteria.createAlias("propertyUtilities", "propUtils");
        //criteria.createAlias("propUtils.utilityDescription", "utilDesc");
        criteria.add(Restrictions.in("propUtils.utilityDescription", utilDescriptions));*/
                //.add(Restrictions.eq("propertyUtilities", utilities))
/*criteria.createAlias("propertyUtilities", "utilList");
        Junction junc = Restrictions.conjunction();
        for(Utility u:utils){
            junc.add(Restrictions.eq("utilList.propertyId", u.getUtilityId()));


        }
        criteria.add(junc);*/



         String address = (String)searchCriteriaMap.get("address");

                if (!address.equals("")){
                 criteria.add(Restrictions.like("adress", address));
                }

        criteria.add(Restrictions.eq("postStatuss", PostStatuss.APPROVED));
        List<Property> propertiesToShow = new ArrayList<>();
        CategoryName categoryName = (CategoryName)searchCriteriaMap.get("postType");
        criteria.createAlias("property.category", "category");
        //criteria.createAlias("category.categoryName", "categoryName");
        List<Property>properties = criteria.add(Restrictions.eq("category.categoryName", categoryName))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();



        for (Property p: properties   ){

            Hibernate.initialize(p.getPropertyOwners());
            Hibernate.initialize(p.getPropertyPhotos());
            Hibernate.initialize(p.getPropertyUtilities());
            propertiesToShow.add(p);
        }

        List<Property>propertiesToSuperShow = new ArrayList<>();
        for(Property p:propertiesToShow){
            List<Utility>utils = p.getPropertyUtilities();
            int utilsSize = utils.size();
            List<Long>uI = new ArrayList<>();
            for(int i=0; i<utilsSize; i++){
                uI.add(utils.get(i).getUtilityId());
            }

            boolean isSubset = uI.containsAll(utilIds);
            if(isSubset){propertiesToSuperShow.add(p); }

        }

        return propertiesToSuperShow;
    }

public List<Property> propertiesByUtilities(Map<String, Object> searchCriteriaMap) throws DBException {
    List<Property>propertyByUtilities = new ArrayList<>();

    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Property.class, "property");
    DetachedCriteria.forClass(Property.class).createAlias("propertyUtilities", "propUtils");
    List<Utility> utilsSubset = (List<Utility>)searchCriteriaMap.get("utils");
    int size = utilsSubset.size();
    String[]utilDescriptions = new String[size];

    for(int i=0; i<size; i++){

        utilDescriptions[i]= utilsSubset.get(i).getUtilityDescription();


    }


    criteria.createAlias("propertyUtilities", "propUtils");
    for(Utility u:utilsSubset){

        criteria.add(Restrictions.eq("propUtils.utilityDescription",u.getUtilityDescription() ));
    }

 return propertyByUtilities;
}

    public Property findApprovedPropertyById( Long propertyId) throws DBException{

        Property property = new Property();
        Session session = sessionFactory.getCurrentSession();
        Property prop = (Property) session.get(Property.class, propertyId);
        Criteria criteria = session.createCriteria(Property.class);
        criteria.add(Restrictions.eq("postStatuss", "APPROVED"));



        Hibernate.initialize(prop.getCategory());
        Hibernate.initialize(prop.getPropertyOwners());
        Hibernate.initialize(prop.getPropertyPhotos());
        Hibernate.initialize(prop.getPropertyUtilities());

        return property;

    }




}

