package lv.javaguru.java2.domain;

/**
 * Created by Viesturs on 10/24/2015.
 */
public class Utility {
    private Long utilityId;
    private String utilityDescription;

    public Long getUtilityId() {return utilityId; }

    public void setUtilityId(Long utilityId) {this.utilityId = utilityId; }

    public String getUtilityDescription() { return utilityDescription; }

    public void setUtilityDescription(String utilityDescription) {  this.utilityDescription = utilityDescription; }


    @Override
    public String toString() {
        return  "UtilityId:" + utilityId +
                ", utilityDescription: " + utilityDescription ;
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
                && utilityDescription.equals(utility.getUtilityDescription())));
    }
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((utilityDescription == null) ? 0 : utilityDescription.hashCode());
        result = prime * result + ((utilityId == null) ? 0 : utilityId.hashCode());


        return result;
    }

    //////////////////////////////////









}
