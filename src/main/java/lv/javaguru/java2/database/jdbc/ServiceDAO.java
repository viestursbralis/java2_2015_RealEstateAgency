package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Property;

import java.util.List;

/**
 * Created by Viesturs on 27-Dec-15.
 */
public interface ServiceDAO {
    List<Property> seeWaitingList(Long agentId) throws DBException;

    Property setAllPropertyLists(Long propertyId) throws DBException;
}
