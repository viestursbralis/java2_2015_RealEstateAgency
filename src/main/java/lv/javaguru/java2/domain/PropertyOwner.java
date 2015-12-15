package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Viesturs on 10/17/2015.
 */

@Entity
@Table(name="property_owner")
public class PropertyOwner {
    @Column(name="PROPERTY_OWNER_ID", columnDefinition="int")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="OWNER_EMAIL")
    private String ownerEmail;
    @Column(name="OWNER_CODE")
    private String ownerPhone;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "propertyOwners")
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
