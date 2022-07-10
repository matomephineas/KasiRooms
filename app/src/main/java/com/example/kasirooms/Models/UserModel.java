package com.example.kasirooms.Models;

public class UserModel {
    private String userID,email,name,contact,address,userType,gender,password;

    public UserModel() {
    }

    public UserModel(String userID, String email, String name, String contact, String address, String userType, String gender, String password) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.userType = userType;
        this.gender = gender;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
