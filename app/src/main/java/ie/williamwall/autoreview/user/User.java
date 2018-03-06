package ie.williamwall.autoreview.user;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class User {

     int avatar;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String timeStamp;

    public User() {
    }


   public User(int avatar, String name, String email, String phone, String password, String timeStamp) {
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.timeStamp = timeStamp;
    }


    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPassword() {
        return this.password;
    }

    public String  getTimeStamp() {return this.timeStamp;}


    public String toString() {
        return "Name: " + name + " \nEmail: " + email + "\nPhone: " + phone + "\nPassword: " + password + "\nTime: " + timeStamp + "\n\n";
    }
}