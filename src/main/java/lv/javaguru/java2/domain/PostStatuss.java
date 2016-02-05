package lv.javaguru.java2.domain;

/**
 * Created by Viesturs on 27-Dec-15.
 */
public enum PostStatuss {

    WAITING, APPROVED;

    public String getStatuss() {
        return this.name();
    }
}
