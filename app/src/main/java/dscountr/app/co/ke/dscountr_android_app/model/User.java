package dscountr.app.co.ke.dscountr_android_app.model;

public class User {

    private int id;
    private String email;
    private String phone_number;
    private String date_of_birth;
    private String gender;
    private String token;
    private String first_name;
    private String last_name;
    private String full_name;

    public User(String email, String phone_number, String date_of_birth, String gender, String first_name, String last_name) {
        this.email = email;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(int id, String first_name, String last_name, String full_name, String email, String phone_number, String date_of_birth, String gender) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
    }

    public User(String phone_number, String email, String token, String first_name, String last_name, String gender, String date_of_birth) {
        this.phone_number = phone_number;
        this.email = email;
        this.token = token;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
    }

    public User(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
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

    public String getToken() {
        return token;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFull_name() {
        return full_name;
    }
}
