package ie.williamwall.autoreview.firebaseReview;

import java.io.Serializable;

/**
 * Created by william on 26/03/2018.
 */

public class Person implements Serializable{

    String uid;
    String name;
    String email;
    String imageUri;



    public Person(String uid, String name, String email, String imageUri) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
    }

    public Person(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
