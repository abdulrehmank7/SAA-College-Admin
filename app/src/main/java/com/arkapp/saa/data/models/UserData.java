package com.arkapp.saa.data.models;

/**
 * Created by Abdul Rehman on 24-07-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
public class UserData {
    String firstname;
    String Lastname;
    String email;
    String phone;
    String username;
    String password;

    public UserData(String firstname, String lastname, String email, String phone,
                    String username, String password) {
        this.firstname = firstname;
        Lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public UserData() {}

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
