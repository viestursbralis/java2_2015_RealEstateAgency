package lv.javaguru.java2.database;

import java.util.List;

/**
 * Created by Viesturs on 10/24/2015.
 */
public interface JunctionDAO {

    void propertyOwnerJunction(Long propertyId, List<Long> ownersId) throws DBException;
    void propertyUtilitiesJunction(Long propertyId, List<Long> utilitiesId) throws DBException;
}
