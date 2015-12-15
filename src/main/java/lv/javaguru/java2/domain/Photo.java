package lv.javaguru.java2.domain;

import javax.persistence.*;

/**
 * Created by Viesturs on 19-Nov-15.
 */
@Entity
@Table(name="photos")
public class Photo {
    @Column(name="PHOTO_ID", columnDefinition="int")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long photoId;
    @Column(name="PHOTO_NAME")
    private String photoName;





    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }


        Photo photo = (Photo) obj;
        return  (((photoId == photo.photoId )|| (photoId!=null && photoId==photo.getPhotoId())))

                && ((photoName == photo.photoName )|| (photoName != null && photoName.equals(photo.getPhotoName()))

                );
    }
    ///////////////////////////////////////////
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((photoId==null) ? 0 : photoId.hashCode());

        result = prime * result
                + ((photoName == null) ? 0 : photoName.hashCode());



        return result;
    }




}
