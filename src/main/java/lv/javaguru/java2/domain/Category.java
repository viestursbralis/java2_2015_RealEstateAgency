package lv.javaguru.java2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Viesturs on 10/19/2015.
 */
@Entity
@Table(name="category")
public class Category {
    @Column(name="CATEGORY_ID", columnDefinition="int")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name="CATEGORY_SHORT_NAME")
    private CategoryName categoryName;
    @Column(name="CATEGORY_DESCRIPTION")
    private String categoryDescription;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL
            , fetch = FetchType.LAZY
            ,orphanRemoval = true
    )
    @JoinTable(name="category_property_junction",
    joinColumns = {@JoinColumn(name="CATEGORY_ID")},
            inverseJoinColumns={@JoinColumn(name="PROPERTY_ID")})
   private List<Property> properties;

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long id) { this.categoryId = id;}


    public String getCategoryDescription() {return categoryDescription;}
    public void setCategoryDescription(String categoryDescription) { this.categoryDescription = categoryDescription; }

    public CategoryName getCategoryName () {return categoryName;}
    public void setCategoryName (CategoryName categoryName) {this.categoryName=categoryName;}

    public List<Property> getProperties() {return properties;}

    public void setProperties(List<Property> properties) { this.properties = properties;}



    public String toString() {
        return "Category name:"  + categoryName + ", Category Id "+ categoryId +", Category Description: "
                + categoryDescription;
    }

    /*private enum CategoryName{
        FLAT_FOR_RENT, FLAT_FOR_SALE, HOUSE_FOR_RENT, HOUSE_FOR_SALE
    }*/
}
