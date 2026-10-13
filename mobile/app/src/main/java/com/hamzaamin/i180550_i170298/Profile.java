package com.hamzaamin.i180550_i170298;

public class Profile {
    String id;
    String email;
    String name;
    String gender;
    String bio;
    String phoneNo;
    String dp;
    //Boolean isOnline;

    public Profile(String id, String email, String name, String gender, String phoneNo, String bio, String dp) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.bio = bio;
        this.dp=dp;
    }
    public Profile(){

    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

