package com.nirajkulkani.demo1;

public class Users {
    String name1 ;
    String phn ;
    String email;
    String password;

    public Users(){}

    public Users(String name1, String phn, String email, String password) {
        this.name1 = name1;
        this.phn = phn;
        this.email = email;
        this.password = password;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
