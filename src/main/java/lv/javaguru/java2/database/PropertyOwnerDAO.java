package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.PropertyOwner;

/**
 * Created by Viesturs on 10/23/2015.
 */
public interface PropertyOwnerDAO {

    Long createPropertyOwner(PropertyOwner propertyOwner) throws DBException;
    PropertyOwner findPropertyOwnerById(Long ownerId) throws DBException;
    void updatePropertyOwner(PropertyOwner owner) throws DBException;




}
