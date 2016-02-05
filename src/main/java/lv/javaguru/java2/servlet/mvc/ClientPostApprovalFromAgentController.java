package lv.javaguru.java2.servlet.mvc;

/**
 * Created by Viesturs on 02-Jan-16.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.ServiceDAO;
import lv.javaguru.java2.domain.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RestController
@Transactional
@EnableWebMvc
public class ClientPostApprovalFromAgentController {//Ajax controller;

    @Autowired
    @Qualifier("ORM_PropertyDAO")
    private PropertyDAO propertyDao;

    @Autowired
    @Qualifier("ORM_ServiceDAO")
    private ServiceDAO serviceDAO;

    @Autowired
    @Qualifier("ORM_PropertyOwnerDAO")
    private PropertyOwnerDAO propertyOwnerDao;


    @RequestMapping(value = "submitEdited", method = {RequestMethod.POST}, headers = "Accept=*/*")
    public ModelAndView submitWaitingPost( HttpServletRequest request) {
        //public @ResponseBody String[] excecute( ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("juniorAgentLoggedInFirstPage1");

        /*String id = request.getParameter("propertyId");
        Long ID = Long.valueOf(id).longValue();
        String description = request.getParameter("propertyDescription");
        Property property = new Property();
        try {
            property = propertyDao.findPropertyById(ID);
        }catch (DBException e) {
            System.out.println("Error!");
        }*/
        String description = request.getParameter("propertyDescription");
        String priceString = request.getParameter("price");
        double price = Long.parseLong(priceString);
        String areaString = request.getParameter("area");
        Long area = Long.valueOf(areaString);
        String landAreaString = request.getParameter("landArea");
        Long landArea = Long.valueOf(landAreaString);
        String countOfBedroomsString = request.getParameter("countOfBedrooms");
        int countOfBedrooms = Integer.parseInt(countOfBedroomsString);
        String pStatuss = request.getParameter("postStatuss");
        System.out.println("Statuss:"+pStatuss);
        PostStatuss postStatuss = PostStatuss.valueOf(pStatuss);
        HttpSession session = request.getSession();
        Property property = (Property)session.getAttribute("property");
        System.out.println("Property from form controller:"+property.toString());
        List<PropertyOwner> existingOwners =property.getPropertyOwners();

                int size = existingOwners.size();
                    existingOwners.clear();
                for(int i=0; i<size; i++){
            String identifier ="propertyOwners["+i+"].id";
            String oid = request.getParameter(identifier);

            Long ownerId = Long.valueOf(oid).longValue();
            PropertyOwner o = new PropertyOwner();


            String firstName = "propertyOwners["+i+"].firstName";
            String ownerFirstName = request.getParameter(firstName);
            String lastName = "propertyOwners["+i+"].lastName";
            String ownerLastName = request.getParameter(lastName);
            String email = "propertyOwners["+i+"].ownerEmail";
            String ownerEmail = request.getParameter(email);
            String phone  = "propertyOwners["+i+"].ownerPhone";
            String ownerPhone = request.getParameter(phone);

            o.setId(ownerId);
            o.setFirstName(ownerFirstName);
            o.setLastName(ownerLastName);
            o.setOwnerEmail(ownerEmail);
            o.setOwnerPhone(ownerPhone);
System.out.println("Owner: "+o.toString());
            existingOwners.add(o);

        }
/************************************************************/
        List<Utility> utils = property.getPropertyUtilities();
        List<Utility>utilsToSave = new ArrayList<>();

        int utilSize = utils.size();

        String[] on = new String[5];
        String[]uD = new String[5];
        for(int i = 0; i<5; i++){
            Utility u = new Utility();
            String to_check="propertyUtilities["+i+"].checked";

            on[i]=request.getParameter(to_check);
            System.out.println("String to_check:"+on[i]);
            boolean checked;
            if (on[i]!=null && on[i].equals("true")){checked=true;
                String udCheck = "propertyUtilities["+i+"].utilityDescription";
                uD[i]=request.getParameter(udCheck);
                System.out.println("Util description:"+uD[i]);
                //Long utilityId = new Long(i);
                String uId = "propertyUtilities["+i+"].utilityId";
                String uIdString = request.getParameter(uId);
                Long utilityId = Long.valueOf(uIdString).longValue();
                System.out.println("Id: "+utilityId);
                u.setUtilityId(utilityId);
                u.setUtilityDescription(uD[i]);
                u.setChecked(checked);
                utilsToSave.add(u);


            }

        }
List<Photo>propertyPhotos = property.getPropertyPhotos(); //photos in property which is saved in session  - before update
        int photoListSize = propertyPhotos.size();
        String[] pC = new String[photoListSize];
        String[]pN = new String[photoListSize];
        List<Photo>photosToSave = new ArrayList<>();
        propertyPhotos.clear();
        for(int i=0; i<photoListSize; i++){
            Photo p = new Photo();
            String photo_check="propertyPhotos["+i+"].checked";

            pC[i]=request.getParameter(photo_check);
            System.out.println("Photo to_check:"+pC[i]);
            boolean photoChecked;
            if (on[i]!=null && on[i].equals("true")){photoChecked=true;
                String phName = "propertyPhotos["+i+"].photoName";
                pN[i]=request.getParameter(phName);
                System.out.println("Photo name:"+pN[i]);

                String pId = "propertyPhotos["+i+"].photoId";
                String pIdString = request.getParameter(pId);
                Long photoId = Long.valueOf(pIdString).longValue();
                System.out.println("Photo Id: "+photoId);
                p.setPhotoId(photoId);
                p.setPhotoName(pN[i]);
                p.setChecked(photoChecked);
                photosToSave.add(p);


            }

        }

            propertyPhotos=photosToSave;

        /********************************************************/



        System.out.println(utils.toString());
        property.setPropertyDescription(description);
        property.setPrice(price);
        property.setArea(area);
        property.setLandArea(landArea);
        property.setCountOfBedrooms(countOfBedrooms);
        property.setPostStatuss(postStatuss);
        property.setPropertyPhotos(propertyPhotos);
        property.setPropertyUtilities(utilsToSave);

        property.setPropertyOwners(existingOwners);

        try {
            propertyDao.mergeProperty(property);//previously = update();
        }catch (DBException e) {
            System.out.println("Error!");
        }
        return modelAndView;
        }



    //@RequestMapping(value = "/submitEdited", method = {RequestMethod.POST}, headers = "Accept=*/*")
    /*public @ResponseBody String execute(@RequestBody Property property) {
        //public @ResponseBody String[] excecute( ) {

        Long id = property.getPropertyId();



        Property currentProperty;
        try {
            currentProperty = propertyDao.findPropertyById(id);

            if (currentProperty == null) {
                System.out.println("Property with id " + id + " not found!");
                //return new ResponseEntity<Property>(HttpStatus.NOT_FOUND);
            }

            currentProperty.setPropertyDescription(property.getPropertyDescription());
            currentProperty.setPostStatuss(property.getPostStatuss());
            //etc....

            propertyDao.update(currentProperty);


            // userService.updateUser(currentUser);
        } catch (DBException e) {
            System.out.println("Error!");
        }
String answer = "Form are updated successfully!";

        //return new ResponseEntity<Property>(currentProperty, HttpStatus.OK);
        return answer;

    }*/
    /***********************************************************************************************************/
    //@RequestMapping(value = "/submitEdited", method = {RequestMethod.POST}, headers = "Accept=*/*", produces = "application/json")
    /*public @ResponseBody
    void excecute1(@RequestBody Property property, @RequestParam(value = "delete", required = false) String delete) {
        //public @ResponseBody String[] excecute( ) {

        Long id = property.getPropertyId();

        if (delete != null && delete.equals("Delete!")) {

            System.out.println("Deleted!");
            System.exit(0);
        }

        Property currentProperty = new Property();
        try {
            currentProperty = propertyDao.findPropertyById(id);

            if (currentProperty == null) {
                System.out.println("Property with id " + id + " not found!");
                //return new ResponseEntity<Property>(HttpStatus.NOT_FOUND);
            }

            currentProperty.setPropertyDescription(property.getPropertyDescription());
            currentProperty.setPostStatuss(property.getPostStatuss());
            //etc....

            propertyDao.update(currentProperty);


            // userService.updateUser(currentUser);
        } catch (DBException e) {
            System.out.println("Error!");
        }

        String names[];
        names = new String[]{"Ankit", "Bohra", "Xyz"};

        //return new ResponseEntity<Property>(currentProperty, HttpStatus.OK);

    }*/
    /**********************************************************************************************/
  //  @RequestMapping(value = "/submitEdited/delete", method = {RequestMethod.POST}, headers = "Accept=*/*", produces = "application/json")
    //public @ResponseBody String ex( @RequestBody final String json) {
    // public @ResponseBody String ex( HttpServletRequest request) {
    // String delete = request.getParameter("delete");
    //String id = request.getParameter("propertyId");
    //Long propertyId = Long.parseLong(id);
    //String result = delete +id;
       /*ObjectMapper mapper = new ObjectMapper();
            String delete = "";
        Map<String, String> ids;
        String result = "";
        try {
            /*ids = mapper.readValue(json, HashMap.class);
            String id = ids.get("propertyId");
            delete = ids.get("delete");

            Long propertyId = new Long(id);
            result = mapper.writeValueAsString(json);


        } catch(Exception e){}*/


/*
        if(delete!=null&& delete.equals("Delete!")) {
                /*try{
                propertyDao.delete(id);

            }catch (DBException e) {
                    System.out.println("Error!");
                }*/
            /*System.out.println("Deleted!");
            System.exit(0);
        }*/
    // Long id = new Long(178);

    // return json;
//}
    //@RequestMapping(value = "/submitEdited/delete", method = {RequestMethod.POST}, headers = "Accept=*/*", produces = "application/json")
    /*public @ResponseBody String delete(@RequestBody PropertyDeleteJson propertyToDeleteJson)
    {

        Long idToDelete = propertyToDeleteJson.getPropertyId();
        String message = "";
if (idToDelete!=null){
    message = "Property deleted successfully!";
}


        return message;
    }*/











    /*********************************************************************************************/




    }






