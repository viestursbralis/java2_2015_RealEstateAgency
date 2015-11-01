package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.PropertyOwner;

/**
 * Created by Viesturs on 10/23/2015.
 */
public interface PropertyOwnerDAO {

    Long create(PropertyOwner propertyOwner) throws DBException;
}
