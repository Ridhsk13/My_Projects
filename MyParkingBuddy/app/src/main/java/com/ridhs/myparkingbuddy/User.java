package com.ridhs.myparkingbuddy;

import java.util.ArrayList;

/**
 * Created by RIDHS on 9/30/2017.
 */

public class User {

    String user_name;
    String email;
    String phone;
    String pref1;
    String pref2;
    String pref3;
    String school;
    ArrayList<String> friends;
    Double latitude;
    Double longitude;
    public User(String user_name, String email, String phone, String pref1, String pref2, String pref3, String school, ArrayList<String> friends, Double latitude, Double longitude) {
        this.user_name = user_name;
        this.email = email;
        this.phone = phone;
        this.pref1 = pref1;
        this.pref2 = pref2;
        this.pref3 = pref3;
        this.school = school;
        this.friends = friends;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPref1() {
        return pref1;
    }

    public void setPref1(String pref1) {
        this.pref1 = pref1;
    }

    public String getPref2() {
        return pref2;
    }

    public void setPref2(String pref2) {
        this.pref2 = pref2;
    }

    public String getPref3() {
        return pref3;
    }

    public void setPref3(String pref3) {
        this.pref3 = pref3;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
