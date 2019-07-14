package dscountr.app.co.ke.dscountr_android_app.model;

public class User {

    private String email;
    private String phone_number;
    private String date_of_birth;
    private String gender;

    public User(String email, String phone_number, String date_of_birth, String gender) {
        this.email = email;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
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
}
