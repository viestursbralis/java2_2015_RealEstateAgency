package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 14-Jan-16.
 */

import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.ServiceDAO;
import lv.javaguru.java2.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RestController
@Transactional
@EnableWebMvc
public class BigSearchController {//Ajax controller;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;


    @Autowired
    @Qualifier("JDBC_UtilityDAO")
    private UtilityDAO utilityDao;

    @Autowired
    @Qualifier ("ORM_CategoryDAO")
    private CategoryDAO categoryDao;

    @RequestMapping(value = "/bigSearch", method = {RequestMethod.GET}, headers = "Accept=*/*", produces = "application/json")


    public
    @ResponseBody
    List<Property> execute(HttpServletRequest request) throws DBException {



        List<String> searchCriteria = new ArrayList<>();
        Map<String, Object> searchCriteriaMap = new HashMap<String, Object>();

        String postType = request.getParameter("postType");
        searchCriteria.add(postType);


        Category category = null;
        CategoryName categoryName =   CategoryName.valueOf(postType);
        searchCriteriaMap.put("postType", categoryName);


        String minPriceString = request.getParameter("lowerPrice");
        searchCriteria.add(minPriceString);
        Double minPrice = new Double(0);
        if (!minPriceString.equals("")){double minP = Double.parseDouble(minPriceString);
        minPrice = new Double(minP);
        }
        searchCriteriaMap.put("minPrice", minPrice);


        String maxPriceString = request.getParameter("upperPrice");
        searchCriteria.add(maxPriceString);
        Double maxPrice = new Double(0);
        if(!maxPriceString.equals("")){double maxP = Double.parseDouble(maxPriceString);
        maxPrice=new Double(maxP);
        }
        searchCriteriaMap.put("maxPrice", maxPrice);

        for (String name: searchCriteriaMap.keySet()){

            String key =name.toString();
            String value = searchCriteriaMap.get(name).toString();
            System.out.println(key + " " + value);
        }

        String address = request.getParameter("adress");
        searchCriteria.add(address);
        searchCriteriaMap.put("address", address);

        String minAreaString = request.getParameter("lowerLivingArea");
        searchCriteria.add(minAreaString);
        Long minArea = new Long(0);
        if(!minAreaString.equals("")){minArea = Long.valueOf(minAreaString);}
        searchCriteriaMap.put("minArea", minArea);


        String maxAreaString = request.getParameter("upperLivingArea");
        searchCriteria.add(maxAreaString);
        Long maxArea = new Long(0);
        if(!maxAreaString.equals("")){maxArea = Long.valueOf(maxAreaString);}
        searchCriteriaMap.put("maxArea", maxArea);


        String minCountOfBedrooms = request.getParameter("lowerCountOfBedrooms");
        searchCriteria.add(minCountOfBedrooms);
        int minBedrooms = 0;
        if(!minCountOfBedrooms.equals("")){minBedrooms = Integer.valueOf(minCountOfBedrooms);}
        searchCriteriaMap.put("minBedrooms", minBedrooms);



        String maxCountOfBedrooms = request.getParameter("upperCountOfBedrooms");
        searchCriteria.add(maxCountOfBedrooms);
        int maxBedrooms = 0;
        if(!maxCountOfBedrooms.equals("")){maxBedrooms = Integer.valueOf(maxCountOfBedrooms);}
        searchCriteriaMap.put("maxBedrooms", maxBedrooms);


        String minLandAreaString = request.getParameter("lowerLandArea");
        searchCriteria.add(minLandAreaString);
        Long minLandArea = new Long(0);
        if(!minLandAreaString.equals("")){minLandArea = Long.valueOf(minLandAreaString);}
        searchCriteriaMap.put("minLandArea", minLandArea);


        String maxLandAreaString = request.getParameter("upperLandArea");
        searchCriteria.add(maxLandAreaString);
        Long maxLandArea = new Long(0);
        if(!maxLandAreaString.equals("")){maxLandArea = Long.valueOf(maxLandAreaString);}
        searchCriteriaMap.put("maxLandArea", maxLandArea);

        Long i = new Long(0);
        Long cg = new Long(0);
        Long ch = new Long(0);
        Long cw = new Long(0);
        Long cs = new Long(0);
        Long checked = new Long(1);


        if (request.getParameter("internet") != null) {
            i = checked;
        }

        if (request.getParameter("city_gas") != null) {
            cg = checked;
        }

        if (request.getParameter("city_heat") != null) {
            ch = checked;
        }

        if (request.getParameter("city_water") != null) {
            cw = checked;
        }

        if (request.getParameter("city_sewer") != null) {
            cs = checked;
        }


        List<Long> utilities = new ArrayList<>();

        utilities.add(i);
        utilities.add(cg);
        utilities.add(ch);
        utilities.add(cw);
        utilities.add(cs);
        List<Utility> utils = new ArrayList<>();
        List<Property> filteredProperties = new ArrayList<>();

        try {
            category=categoryDao.findCategoryByName(categoryName);
            utils = utilityDao.createUtility(utilities);
            for(Utility u:utils){
                u.setChecked(false);
                System.out.println("Utils u from inside a checked set:"+u.toString());
            }
            for(Utility u:utils){

                System.out.println("Utils u from outside a checked set:"+u.toString());
            }

            searchCriteriaMap.put("utils", utils);
            /*filteredProperties = propertyDao.filterByCriteria(minPrice, maxPrice, minBedrooms, maxBedrooms,
                    minLandArea, maxLandArea, minArea, maxArea, utils, address, category);*/
            filteredProperties = propertyDao.filterByCriteriaMap(searchCriteriaMap);
            //filteredProperties = propertyDao.propertiesByUtilities(searchCriteriaMap);

            for(Property p:filteredProperties){
                List<Utility>utilsFromProp = p.getPropertyUtilities();

                for(Utility u:utilsFromProp){
                    System.out.println("utilities from property:"+u.toString());
                }
            }


        } catch (final Throwable t) {
          throw t;
        }

        //return propertiesOfThisUser;
        return filteredProperties;


    }
}



