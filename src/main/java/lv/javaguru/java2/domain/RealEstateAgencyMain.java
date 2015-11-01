package lv.javaguru.java2.domain;

/**
 * Created by Viesturs on 10/19/2015.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.*;

import static lv.javaguru.java2.domain.Statuss.*;


public class RealEstateAgencyMain {
    private static PropertyDAO propertyDao = new PropertyDAOImpl();
    private static UserDAO userDao = new UserDAOImpl();
    private static AgentDAO agentDao = new AgentDAOImpl();
    private static PropertyOwnerDAO propertyOwnerDao= new PropertyOwnerDAOImpl();
    private static JunctionDAO junctionDao = new JunctionDAOImpl();
    private static UtilityDAO propertyUtilityDao = new UtilityDAOImpl();
    private static CategoryDAO categoryDao = new CategoryDAOImpl();
    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in).useDelimiter("\\n");
        User user = null;

        System.out.println("Message to RealEstate clients!");

        int choice;
/**************************************************************************************/
        try {
            System.out.println("This real estate agent will be set as responsible for your property. If you have \n" +
                    "any questions, please contact the agent (comment - program finds less busy agent and sets it automatically):");
            System.out.println(agentDao.findLessBusyAgent());
        } catch (DBException e) {
            System.out.println("Error!");
        }


        /**************************************************************************/
System.out.println("You have three options. You can browse our site without signing in. \n" +
        "In this case you can not post any advertisments. \n" +
        "You can sign in if you are registered user or you can register right now! \n" +
        "To continue without signing in press 1, to sign in press 2, to register pres 3:");

        int option=scan.nextInt();
        if (option==1){
            System.out.println("Welcome, guest!");
            System.out.println("You can browse our RealEstate collection by following options:");
        }
        else if(option==2) {

            System.out.println("Enter your username:");
            String userName = scan.next();
            System.out.println("Enter your password:");
            String userPassword = scan.next();
            try {
                Statuss statuss = checkCredentials(userName, userPassword);

                System.out.println(statuss);
                switch (statuss) {
                    case CLIENT:
                        //List<Property> allProperties = propertyDao.findAllProperties();

                        user = userDao.findUserByCredentials(userName, userPassword);

                        //setListOfUserPropertiesMain(user);
                        List<Property>propertiesByThisUser=propertyDao.findPropertyByClient(user);
                        user.setListOfProperties(propertiesByThisUser);

                        System.out.println("This is current (logged in) user:");
                        System.out.println(user);

                        String loggedInUserFirstName=user.getFirstName();
                        String loggedInUserLastName = user.getLastName();

                        System.out.println("Welcome, "+loggedInUserFirstName+ " "+loggedInUserLastName);
                        do {
                            System.out.println(" There are a list of available options for clients:\n" +
                                    "1 - add a new RealEstate to the database \n" +
                                    "2 - list all RealEstate's in the database\n" +
                                    "3 - search for specific RealEstate in the database\n" +
                                    "4 - delete a specific RealEstate from the database\n" +
                                    "5 - exit");

                            System.out.println(" Enter Your option:");

                            choice = scan.nextInt();


                            switch (choice) {
                                case 1:

                                    Property property = createPropertyMain();
                                    property.setClient(user);

                                    Long lastPropertyID = propertyDao.create(property);//save property into database and
                                    // returns a last inserted Id in properties;
                                    //insert a list of property owners into database;
                                    List<PropertyOwner> propertyOwnersMain = property.getPropertyOwners();
                                    List<Long> propertyOwnersID = new ArrayList<>();
                                    Long lastPropertyOwnerID = null;
                                    for (PropertyOwner propertyOwner : propertyOwnersMain) {
                                        lastPropertyOwnerID = propertyOwnerDao.create(propertyOwner);
                                        propertyOwnersID.add(lastPropertyOwnerID);
                                    }
                                    junctionDao.propertyOwnerJunction(lastPropertyID, propertyOwnersID);

                                    //insert a list of property utilities into junction table;

                                    List<Long> propertyUtilitiesId = new ArrayList<>();

                                    List<Utility> propertyUtilities = new ArrayList<>();
                                    propertyUtilities = property.getPropertyUtilities();
                                    for (Utility utils : propertyUtilities) {
                                        propertyUtilitiesId.add(utils.getUtilityId());

                                    }
                                    junctionDao.propertyUtilitiesJunction(lastPropertyID, propertyUtilitiesId);

                                    System.out.println("Your property has been successfully registered in our database!Wait for calls!");
                                    break;


                                case 2:
                                    findAllPropertiesMain();


                                    break;

                                case 3:

                                    System.out.println("Enter a property Category ID to search:");
                                    Long categoryId = scan.nextLong();
                                    List<Property> allProperties = findAllPropertiesMainList();
                                    List<Property> propertiesByCategoryId = propertyDao.findPropertyByCategoryId(allProperties, categoryId);
                                    for (Property prop : propertiesByCategoryId) {
                                        System.out.println(prop.toString());
                                    }
                                    System.out.println("Properties by price range:");
                                    System.out.println("Enter a lower level of your price range:");
                                    double min = scan.nextDouble();
                                    System.out.println("Enter an upper level of your price range:");
                                    double max = scan.nextDouble();
                                    List<Property> propertiesByPriceRange = new ArrayList<>();
                                    propertiesByPriceRange = propertyDao.findPropertyByPriceRange(allProperties, min, max);
                                    for (Property prop : propertiesByPriceRange) {
                                        System.out.println(prop);
                                    }
                                    List<Long> searchForUtilities = new ArrayList<>();
                                    System.out.println("Find a property by utilities. Enter a 1 if utility is present, 0 - if not. ");
                                    System.out.println("Is there a broadband internet (cable) available:");
                                    Long broadbandInternet = scan.nextLong();
                                    System.out.println("Is there a city gas available:");
                                    Long cityGas = scan.nextLong();
                                    System.out.println("Is there a city heat available:");
                                    Long cityHeat = scan.nextLong();
                                    System.out.println("Is there a city water available:");
                                    Long cityWater = scan.nextLong();
                                    System.out.println("Is there a city sewer available:");
                                    Long citySewer = scan.nextLong();

                                    searchForUtilities.add(broadbandInternet);
                                    searchForUtilities.add(cityGas);
                                    searchForUtilities.add(cityHeat);
                                    searchForUtilities.add(cityWater);
                                    searchForUtilities.add(citySewer);
                                    List<Utility> searchUtils = new ArrayList<>();
                                    try {
                                        searchUtils = propertyUtilityDao.createUtility(searchForUtilities);

                                    } catch (DBException e) {
                                        System.out.println("Error!");
                                    }
                                    List<Property> propertiesByUtilities = propertyDao.findPropertiesWithCertainUtilities(allProperties, searchUtils);
                                    for (Property prop : propertiesByUtilities) {
                                        System.out.println(prop.toString());
                                    }


                                    break;
                                case 4:

                                    List<Property> propertiesOfThisUser = user.getListOfProperties();
                                    System.out.println(user);
                                    System.out.println("You can delete only those RealEstate ads posted by you. \n" +
                                            "There are a list of properties added by you:");
                                    for (Property prop : propertiesOfThisUser) {

                                        System.out.println(prop);

                                    }

                                    System.out.println("Enter an Id of property, which you want to delete:");
                                    Long propertyIdToDelete = scan.nextLong();
                                    propertyDao.delete(propertyIdToDelete);
                                    System.out.println("Property with Id " + propertyIdToDelete + " was successfully deleted from our database!");
                                    //setListOfUserPropertiesMain(user);
                                    break;
                                case 5:

                                    break;


                            }
                        }
                        while (choice != 5);
                        break;


                    case JUNIOR:

                        Agent juniorAgent = agentDao.findAgentByCredentials(userName, userPassword);

                        System.out.println("Welcome,junior agent!");

                        do {
                            System.out.println(" There are a list of available options for junior agents:\n" +
                                    "1 - add a new RealEstate to the database \n" +
                                    "2 - list all RealEstate's in the database\n" +
                                    "3 - search for specific RealEstate in the database\n" +
                                    "4 - search for specific RealEstate by the owner in the database\n" +
                                    "5 - delete a specific RealEstate from the database\n" +
                                    "6- exit");

                            System.out.println(" Enter Your option:");

                            choice = scan.nextInt();


                            switch (choice) {
                                case 1:


                                    break;


                                case 2:
                                    List<Property> allPropertiesJunior = propertyDao.findAllProperties();
                                    for (Property prop : allPropertiesJunior) {
                                        System.out.println(prop);
                                    }
                                    break;

                                case 3:


                                    break;
                                case 4:
                                    //search property by owner;
                                    System.err.println("Search properties by owner keyword: V:");
                                    try {
                                        findPropertyByOwnerMain("V");
                                    } catch (DBException e) {
                                        System.out.println("Error!");
                                    }


                                    break;
                                case 5:

                                    break;


                            }
                        }
                        while (choice != 6);


                        break;

                    case SENIOR:

                        Agent seniorAgent = agentDao.findAgentByCredentials(userName, userPassword);
                        System.out.println("Welcome,senior agent!");


                        do {
                            System.out.println(" There are a list of available options for junior agents:\n" +
                                    "1 - add a new RealEstate to the database \n" +
                                    "2 - list all RealEstate's in the database\n" +
                                    "3 - search for specific RealEstate in the database\n" +
                                    "4 - search for specific RealEstate by the owner in the database\n" +
                                    "5 - delete a specific RealEstate from the database\n" +
                                    "6 - add a new agent to database\n" +
                                    "7 - delete an agent from database\n" +
                                    "8 - change an agent statuss\n" +
                                    "9- exit");

                            System.out.println(" Enter Your option:");

                            choice = scan.nextInt();


                            switch (choice) {
                                case 1:


                                    break;


                                case 2:

                                    break;

                                case 3:


                                    break;
                                case 4:
                                    //search property by owner;
                                    System.err.println("Search properties by owner keyword: V:");
                                    try {
                                        findPropertyByOwnerMain("V");
                                    } catch (DBException e) {
                                        System.out.println("Error!");
                                    }


                                    break;
                                case 5:

                                    break;

                                case 6:

                                    break;

                                case 7:

                                    break;
                                case 8:

                                    break;

                                case 9:

                                    break;

                            }
                        }
                        while (choice != 9);


                        break;

                }


            } catch (DBException e) {
                System.out.println("Error!");
            }


        }
        else if (option==3){
            System.out.println("You need to fill out following form in order to register:");
            System.out.println("Enter your first name:");
            String userFirstName=scan.next();
            System.out.println("Enter you last name:");
            String userLastName=scan.next();
            System.out.println("Enter your contact email:");
            String userEmail=scan.next();
            System.out.println("Enter your password:");
            String userPassword=scan.next();

            System.out.println("Press 1 to finish registration, press 0 to cancel:");
            int registrationChoice=scan.nextInt();
            if(registrationChoice==1){
                User newUser= new User();
                newUser.setFirstName(userFirstName);
                newUser.setLastName(userLastName);
                newUser.setUserEmail(userEmail);
                newUser.setPassword(userPassword);
                newUser.setStatuss(Statuss.CLIENT);
                try {
                    userDao.create(newUser);
                } catch (DBException e) {
                    System.out.println("Error!");
                }

            }
            else if(registrationChoice==0){

            }
            else {System.out.println("Be reasonable - enter a valid choice, jerk!"); }

        }

        else  {
            System.out.println("You entered a wrong choice! Thing again and choose appropriate!");

        }


    }

        /*********************************************************************************************/


    /***************************************************************************************************/

    private static void findPropertyByOwnerMain(String ownerName) throws DBException {
        List<Property> properties = propertyDao.findPropertyByOwnerKeyWord(ownerName);
        for (Property property : properties) {
            System.out.println(property);
        }

    }

    /**********************************************************************************/
   private static void findPropertiesWithCertainUtilitiesMain(Long[] utilities) throws DBException {
        List<Property> properties = propertyDao.findPropertiesWithCertainUtilities(utilities);
        for (Property property : properties) {
            System.out.println(property);
        }

    }

    /********************************************************************************/
    private static void findAllPropertiesMain() throws DBException {

        List<Property> properties = propertyDao.findAllProperties();
        for (Property property : properties) {
            System.out.println(property);
        }
    }

    private static List<Property> findAllPropertiesMainList() throws DBException{


        return propertyDao.findAllProperties();
    }

    /*private static void searchBooks(String keyWord) throws DBException {

        List<Property> books = propertyDao.findPropertyByKeyword(keyWord);
        for (Property book : books) {
            System.out.println(book);
        }
    }*/

    private static Statuss checkCredentials(String username, String password) throws DBException {
        Statuss statuss = null;



       if (agentDao.findAgentByCredentials(username, password).getAgentStatuss()==Statuss.JUNIOR) {

            statuss = JUNIOR;
           return statuss;
        }
        else if (agentDao.findAgentByCredentials(username, password).getAgentStatuss()==Statuss.SENIOR){

           statuss = JUNIOR;
           return statuss;
       }
       else if (userDao.findUserByCredentials(username, password).getStatuss()==Statuss.CLIENT) {
            statuss=CLIENT;
            return statuss;
        }

       return statuss;
    }


    private static void createUserMain(User user) throws DBException {
        userDao.create(user);

    }

    /*private static Agent findLessBusyAgentMain() throws DBException{
        Long[] agentsIndex=new Long[2];

        agentsIndex=propertyDao.findLessBusyAgentID();
        Agent agent=new Agent();
        agent=agentDao.getAgentById(agentsIndex[0]);


        return agent;
}*/
    /***********************************************************************************************/
    public static Property createPropertyMain(){
        Property property=new Property();
        Long[] agentsIndex=new Long[2];
        //Scanner scan=new Scanner(System.in);
        Scanner scan = new Scanner(System.in).useDelimiter("\\n");
        System.out.println("Enter a category (from available categories):");
        String propertyCategory=scan.next();
        CategoryName categoryName = CategoryName.valueOf(propertyCategory);
        Category category=new Category();
            try {
                category = categoryDao.findCategoryByName(categoryName);
                property.setCategory(category);
            }catch (DBException e) {
                System.out.println("Error!");
            }
        /*Agent lessBusyAgent=new Agent();
        try {
            lessBusyAgent = propertyDao.findLessBusyAgent();

            property.setAgent(lessBusyAgent);
        }
        catch (DBException e) {
            System.out.println("Error!");
        }*/


        System.out.println("Enter a property description:");
        String propertyDescription=scan.next();
        property.setPropertyDescription(propertyDescription);

        System.out.println("Enter a property price:");
        Double propertyPrice= scan.nextDouble();
        property.setPrice(propertyPrice);
        System.out.println("Enter a property adress:");
        String propertyAdress=scan.next();
        property.setAdress(propertyAdress);

        System.out.println("Enter a property area:");
        Long propertyArea=scan.nextLong();
        property.setArea(propertyArea);
        System.out.println("Enter a bedroom count:");
        int propertyBedrooms=scan.nextInt();
        property.setCountOfBedrooms(propertyBedrooms);
        System.out.println("Enter a land area:");
        Long propertyLandArea=scan.nextLong();
        property.setLandArea(propertyLandArea);
        int choice=0;
        List<PropertyOwner> propertyOwners=new ArrayList<>();
        do {
            System.out.println("Enter a property owner data:");
            PropertyOwner propertyOwner = new PropertyOwner();
            System.out.println("Enter a property owner first name:");
            String ownerFirstName = scan.next();
            propertyOwner.setFirstName(ownerFirstName);
            System.out.println("Enter a property owner last name:");
            String ownerLastName = scan.next();
            propertyOwner.setLastName(ownerLastName);
            System.out.println("Enter a property owner email:");
            String ownerEmail = scan.next();
            propertyOwner.setOwnerEmail(ownerEmail);
            System.out.println("Enter a property owner social security number:");
            String ownerPhone = scan.next();
            propertyOwner.setOwnerPhone(ownerPhone);

            propertyOwners.add(propertyOwner);

            System.out.println("If you want to add another property owner, enter 1, else enter 0:");

            choice=scan.nextInt();
        }
        while(choice ==1);
        property.setPropertyOwners(propertyOwners);
        List<Long> utilities=new ArrayList<>();
System.out.println ("Specify which utilities is available in this property (press 1 if available, press 0 if not available):");
System.out.println("Is there a broadband internet (cable) available:");
        Long  broadbandInternet=scan.nextLong();
        System.out.println("Is there a city gas available:");
        Long cityGas=scan.nextLong();
        System.out.println("Is there a city heat available:");
        Long cityHeat= scan.nextLong();
        System.out.println("Is there a city water available:");
        Long cityWater = scan.nextLong();
        System.out.println("Is there a city sewer available:");
        Long citySewer = scan.nextLong();

        utilities.add(broadbandInternet);
        utilities.add(cityGas);
        utilities.add(cityHeat);
        utilities.add(cityWater);
        utilities.add(citySewer);

        /*for (Long util:utilities) {
            System.out.println(util);//working  - printing List of Long's a.k.a. 1,0,1,0,1;
        }*/


        List<Utility> propertyUtils=new ArrayList <>();
        try {
            propertyUtils=propertyUtilityDao.createUtility(utilities);

            property.setPropertyUtilities(propertyUtils);
        }
        catch (DBException e) {
            System.out.println("Error!");
        }


        return property;
    }

/*********************************************************************************************/
    private static  User setListOfUserPropertiesMain (User user) throws DBException {
        List<Property> listOfClientProperties = new ArrayList<>();
        List<Property>allProperties=findAllPropertiesMainList();
        Long userId=user.getUserId();
System.out.println(userId);

        listOfClientProperties=propertyDao.findPropertyByClient(allProperties, userId);
        for (Property prop:listOfClientProperties){
            System.out.println(prop);
            System.out.println("********************");
        }

        user.setListOfProperties(listOfClientProperties);

        return user;
    }


    /***********************************************************************************************/
    private static void insertPropertyMain(Property property){}

    /*********************************************************************************************/


     }
