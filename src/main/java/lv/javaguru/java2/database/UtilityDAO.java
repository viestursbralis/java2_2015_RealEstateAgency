package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Utility;

import java.util.List;

/**
 * Created by Viesturs on 10/24/2015.
 */
public interface UtilityDAO {
List<Utility> createUtility(List<Long> utilityId) throws DBException;


}
