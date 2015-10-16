package com.example.sandilemazibuko.groovappbeta;

/**
 * Created by sandilemazibuko on 15/10/08.
 */
public class User {
    String user_id;
    String name;
    String surname;
    String profile_pic;
    String password;
    String cellphone;
    String email;
    String dob;
    String membership_type;
    String status;


    public User(String user_id, String name, String surname,
                String profile_pic, String password,
                String cellphone, String email,
                String dob,String membership_type,String status) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.profile_pic = profile_pic;
        this.password = password;
        this.cellphone = cellphone;
        this.email = email;
        this.dob = dob;
        this.membership_type = membership_type;
        this.status = status;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMembership_type() {
        return membership_type;
    }

    public void setMembership_type(String membership_type) {
        this.membership_type = membership_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
