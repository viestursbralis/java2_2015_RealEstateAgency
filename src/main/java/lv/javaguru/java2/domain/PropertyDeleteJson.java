package lv.javaguru.java2.domain;

import org.springframework.stereotype.Component;

/**
 * Created by Viesturs on 05-Jan-16.
 */

@Component
public class PropertyDeleteJson {

    private Long propertyId;


    PropertyDeleteJson(){}

    public Long getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(Long id) {
        this.propertyId = id;
    }

}
