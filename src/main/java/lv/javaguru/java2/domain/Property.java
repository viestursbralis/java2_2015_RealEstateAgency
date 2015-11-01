package lv.javaguru.java2.domain;

/**
 * Created by Viesturs on 10/17/2015.
 */
import java.util.List;


public class Property {
    private Long propertyId;
    private Category category;

    private User client;
    private String propertyDescription;
    private List<PropertyOwner> propertyOwners;
    private List<Utility> propertyUtilities;
    private double price;
    private String adress;
    private Long area;
    private int countOfBedrooms;
    private Long landArea;


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

    public String toString() {
        return "Property Id: " + propertyId + ", Property description: " + propertyDescription + ", Category: " +
                category +  ", Property owners: "
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