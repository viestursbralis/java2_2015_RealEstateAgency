package lv.javaguru.java2.domain;

/**
 * Created by Viesturs on 10/17/2015.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="property")
public class Property {
    @Column(name="PROPERTY_ID", columnDefinition="int")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long propertyId;

    @ManyToOne
    @JoinTable(name = "category_property_junction",
            joinColumns={@JoinColumn(name="PROPERTY_ID")},
    inverseJoinColumns={@JoinColumn(name="CATEGORY_ID")})
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User client;

    @Enumerated(EnumType.STRING)
    @Column(name="POST_STATUSS")
    private PostStatuss postStatuss;

    @Column (name="PROPERTY_DESCRIPTION")
    private String propertyDescription;
    @Column(name="PRICE")
    private double price;
    @Column(name="ADRESS")
    private String adress;
    @Column(name="AREA")
    private Long area;
    @Column(name="COUNT_OF_BEDROOMS")
    private int countOfBedrooms;
    @Column(name="LAND_AREA")
    private Long landArea;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(name="property_owner_junction",
            joinColumns={@JoinColumn(name="PROPERTY_ID")},
            inverseJoinColumns={@JoinColumn(name="PROPERTY_OWNER_ID")})
    private List<PropertyOwner> propertyOwners;


    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="property_utility_junction",
            joinColumns={@JoinColumn(name="PROPERTY_ID")},
            inverseJoinColumns={@JoinColumn(name="UTILITY_ID")})
    //@Filter(name="utilityTrue")
    private List<Utility> propertyUtilities;


    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(name="property_photos_junction",
            joinColumns={@JoinColumn(name="PROPERTY_ID")},
            inverseJoinColumns={@JoinColumn(name = "PHOTO_ID") })
    private List<Photo> propertyPhotos;



    public Long getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(Long id) {
        this.propertyId = id;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public PostStatuss getPostStatuss() {
        return postStatuss;
    }

    public void setPostStatuss(PostStatuss postStatuss) {
        this.postStatuss = postStatuss;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }
    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }
    public List<PropertyOwner> getPropertyOwners() { return propertyOwners; }
    public void setPropertyOwners(List<PropertyOwner> propertyOwner) { this.propertyOwners = propertyOwner;}

    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public int getCountOfBedrooms() {
        return countOfBedrooms;
    }

    public void setCountOfBedrooms(int countOfBedrooms) {
        this.countOfBedrooms = countOfBedrooms;
    }

    public Long getLandArea() {
        return landArea;
    }

    public void setLandArea(Long landArea) {
        this.landArea = landArea;
    }

    public List<Utility> getPropertyUtilities() { return propertyUtilities; }

    public void setPropertyUtilities(List<Utility> propertyUtilities) { this.propertyUtilities = propertyUtilities; }

    public User getClient() { return client;  }

    public void setClient(User client) { this.client = client;}

    public List<Photo> getPropertyPhotos() {
        return propertyPhotos;
    }

    public void setPropertyPhotos(List<Photo> propertyPhotos) {
        this.propertyPhotos = propertyPhotos;
    }




    public String toString() {
        return "Property Id: " + propertyId + ", Property description: " + propertyDescription + ", Price: " + price + ", Category: "
                +  ", Property owners: "
                + propertyOwners + ", Property utilities: " +propertyUtilities +"\n";
                //+ ", Who inserted this property into database: " + client;
    }



    /*******************************************************************************************************/
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }


        Property property = (Property) obj;
        return  ((propertyDescription == property.propertyDescription )|| (propertyDescription != null && propertyDescription.equals(property.getPropertyDescription()))

                && ((propertyOwners == property.propertyOwners )|| (propertyOwners != null && propertyOwners.equals(property.getPropertyOwners())))

                && ((propertyUtilities == property.propertyUtilities )|| (propertyUtilities != null && propertyUtilities.equals(property.getPropertyUtilities())))

                &&  ((price == property.price )|| (price != 0 && price==property.getPrice()))

                &&   ((adress == property.adress )|| (adress != null && adress.equals(property.getAdress())))

                && ((area == property.area )|| (area != null && area.equals(property.getArea())))

                && ((client == property.client )|| (client != null && client.equals(property.getClient())))

                && ((countOfBedrooms == property.countOfBedrooms )|| (countOfBedrooms != 0 && countOfBedrooms==property.getCountOfBedrooms()))

                &&  ((landArea == property.landArea )|| (landArea != null && landArea.equals(property.getLandArea())))

        ) ;
    }
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((propertyDescription == null) ? 0 : propertyDescription.hashCode());
        result = prime * result + ((propertyOwners == null) ? 0 : propertyOwners.hashCode());

        result = prime * result
                + ((propertyUtilities == null) ? 0 : propertyUtilities.hashCode());
        result = prime * result
                + ((price==0) ? 0 : Double.valueOf(price).hashCode());
        result = prime * result
                + ((adress == null) ? 0 : adress.hashCode());
        result = prime * result
                + ((area == null) ? 0 : area.hashCode());

        result = prime * result
                + ((client == null) ? 0 : client.hashCode());

        result = prime * result
                + countOfBedrooms;
        result = prime * result
                + ((landArea == null) ? 0: landArea.hashCode());





        return result;
    }


    /*********************************************************************************************************/


}