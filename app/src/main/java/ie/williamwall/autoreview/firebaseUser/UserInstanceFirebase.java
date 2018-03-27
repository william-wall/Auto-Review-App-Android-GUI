package ie.williamwall.autoreview.firebaseUser;

/**
 * Created by william on 27/03/2018.
 */

public class UserInstanceFirebase {

    private String uid,name,email;

    public UserInstanceFirebase()
    {

    }

    public UserInstanceFirebase(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
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
}
