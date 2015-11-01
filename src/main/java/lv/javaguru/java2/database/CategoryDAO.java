package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Category;
import lv.javaguru.java2.domain.CategoryName;

/**
 * Created by Viesturs on 11/1/2015.
 */
public interface CategoryDAO {

    Category findCategoryByName(CategoryName categoryName) throws DBException;
}
