package lv.javaguru.java2.domain;

import org.hibernate.annotations.FilterDef;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * Created by Viesturs on 10/24/2015.
 */
@Entity
//@FilterDef(name="utilityTrue")
@Table(name="utility")
public class Utility {
    @Column(name="UTILITY_ID", columnDefinition="int")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long utilityId;
    @Column(name="UTILITY_DESCRIPTION")
    private String utilityDescription;

    @Transient
    //@AssertTrue
    private boolean checked;
   // @ManyToMany (mappedBy = "propertyUtilities", cascade=CascadeType.ALL)
    //private List<Property> properties;



    public Long getUtilityId() {return utilityId; }

    public void setUtilityId(Long utilityId) {this.utilityId = utilityId; }

    public String getUtilityDescription() { return utilityDescription; }

    public void setUtilityDescription(String utilityDescription) {  this.utilityDescription = utilityDescription; }

    //public List<Property> getProperties() { return properties; }

   // public void setProperties(List<Property> properties) {this.properties = properties;}


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return  "utilityId:" + utilityId +
                ", utilityDescription: " + utilityDescription +
                ", checked: " + checked;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }


        Utility utility = (Utility) obj;
        return  ((utilityId == utility.utilityId )|| (utilityId != 0 && utilityId==utility.getUtilityId()))

                && ((utilityDescription == utility.utilityDescription) || (utilityDescription !=null
                && utilityDescription.equals(utility.getUtilityDescription())))
                &&((checked==utility.checked) || ((Boolean)checked!=null && checked==utility.isChecked())
                );
    }
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
       Boolean b = new Boolean (checked);
        int result = 1;
        result = prime * result
                + ((utilityDescription == null) ? 0 : utilityDescription.hashCode());
        result = prime * result + ((utilityId == null) ? 0 : utilityId.hashCode());
        result = prime * result
        + b.hashCode();

        return result;
    }

    //////////////////////////////////









}
