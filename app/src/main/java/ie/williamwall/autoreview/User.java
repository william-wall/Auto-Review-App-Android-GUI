package ie.williamwall.autoreview;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class User {

    private String name;
    private String email;
    private int phone;
    private String password;

    User(String name, String email, int phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public int getPhone() {
        return this.phone;
    }

    public String getPassword() {
        return this.password;
    }

    public String toString() {
        return "Name: " + name + " \nEmail: " + email + "\nPhone: " + phone + "Password: " + password + "\n\n";
    }
}