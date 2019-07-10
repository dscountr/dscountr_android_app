package dscountr.app.co.ke.dscountr_android_app.model;

public class User {

    private String email;
    private String username;
    private String phone_number;
    private String date_of_birth;
    private String gender;
    private String password;

    public User(String email, String username, String phone_number, String date_of_birth, String gender, String password) {
        this.email = email;
        this.username = username;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }
}
