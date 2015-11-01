package lv.javaguru.java2.domain;

import java.util.List;

/**
 * Created by Viesturs on 10/17/2015.
 */
public class PropertyOwner {
    private Long id;

    private String firstName;
    private String lastName;
    private String ownerEmail;
    private String ownerPhone;
    List<Property> property;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getOwnerEmail() {return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }
    public String getOwnerPhone() { return ownerPhone;}
    public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }

    public List<Property> getProperty() { return property; }

    public void setProperty(List<Property> property) { this.property = property; }

    public String toString() {
        return "Owner: " + id + ", Owner First Name: "
                + firstName + ", Owner Last Name: " +lastName +", Owner email:" +ownerEmail +", Owner phone: "+ ownerPhone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }


        PropertyOwner propertyOwner = (PropertyOwner) obj;
        return  ((firstName == propertyOwner.firstName )|| (firstName != null && firstName.equals(propertyOwner.getFirstName()))

                && ((lastName == propertyOwner.lastName )|| (lastName != null && lastName.equals(propertyOwner.getLastName())))

                && ((ownerEmail == propertyOwner.ownerEmail )|| (ownerEmail != null && ownerEmail.equals(propertyOwner.getOwnerEmail())))

        &&  ((ownerPhone == propertyOwner.ownerPhone )|| (ownerPhone != null && ownerPhone.equals(propertyOwner.getOwnerPhone())))) ;
    }
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());

        result = prime * result
                + ((ownerEmail == null) ? 0 : ownerEmail.hashCode());
        result = prime * result + ((ownerPhone == null) ? 0 : ownerPhone.hashCode());


        return result;
    }


}
