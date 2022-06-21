package com.example.smartmoney;

public class User {

    private String FName;
    private String LName;
    private String Email;
    private String Address;
    private String Gender;
    private String Prof_list;

    public User(){
    }



    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getProf_list() {
        return Prof_list;
    }

    public void setProf_list(String prof_list) {
        Prof_list = prof_list;
    }
}
