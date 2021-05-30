package com.promoteprovider.todayvalue;

public class User_data {
    private String email,pass,name,referCode;

    public User_data() {
    }

    public User_data(String email, String pass, String name, String referCode) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.referCode = referCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }
}
