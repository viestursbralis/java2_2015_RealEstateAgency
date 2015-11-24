package lv.javaguru.java2.domain;

import java.util.List;

/**
 * Created by Viktor on 01/07/2014.
 */
public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String password;
    private Statuss statuss;

    private Agent agent;
    List<Property> listOfProperties;



    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getPassword() {return password; }

    public void setPassword(String password) { this.password = password;}

    public String getUserEmail() { return userEmail;}

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public List<Property> getListOfProperties() { return listOfProperties;}

    public void setListOfProperties(List<Property> listOfProperties) { this.listOfProperties = listOfProperties; }

    public Statuss getStatuss() { return statuss; }

    public void setStatuss(Statuss statuss) { this.statuss = statuss; }

    public Agent getAgent() {return agent; }

    public void setAgent(Agent agent) { this.agent = agent; }

    public String toString() {
        return "User FirstName: " + firstName + ", LastName: "
                + lastName +", Id: "+ userId + ", User email: "+ userEmail+ ", \nList of user properties: \n" +listOfProperties;
    }
/**********************************************************************************************/
@Override
public boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
        return false;
    }


    User user = (User) obj;
    return  (((firstName == user.firstName )|| (firstName != null && firstName.equals(user.getFirstName())))

            && ((lastName == user.lastName )|| (lastName != null && lastName.equals(user.getLastName())))

            && ((userEmail == user.userEmail )|| (userEmail != null && userEmail.equals(user.getUserEmail())))

            && ((password == user.password )|| (password != null && password.equals(user.getPassword())))

            && ((listOfProperties == user.listOfProperties )|| (listOfProperties != null && listOfProperties.equals(user.getListOfProperties())))

            && ((agent == user.agent )|| (agent != null && agent.equals(user.getAgent())))

            &&  ((statuss == user.statuss )|| (statuss != null && statuss.equals(user.getStatuss())))  );
}
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());

        result = prime * result
                + ((lastName == null) ? 0 : lastName.hashCode());

        result = prime * result
                + ((userEmail == null) ? 0 : userEmail.hashCode());

        result = prime * result
                + ((password == null) ? 0 : password.hashCode());

        result = prime * result
                + ((statuss == null) ? 0 : statuss.hashCode());

        result = prime * result
                + ((listOfProperties == null) ? 0 : listOfProperties.hashCode());

        result = prime * result
                + ((agent == null) ? 0 : agent.hashCode());

        return result;
    }


    /*********************************************************************************************/

}
