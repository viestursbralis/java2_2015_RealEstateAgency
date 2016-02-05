package lv.javaguru.java2.database;


import lv.javaguru.java2.domain.PropertyOwner;

/**
 * Created by Viesturs on 27-Jan-16.
 */
public interface OwnerDAO {

    PropertyOwner findPropertyOwnerById(Long ownerId) throws DBException;
    void updateOwner(PropertyOwner owner) throws DBException;


}
