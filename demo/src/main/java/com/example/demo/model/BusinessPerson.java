package com.example.demo.model;

import java.util.Objects;

public class BusinessPerson extends User{

    private int businessID;
    private int NIP;
    private String role;
    private float rating;
    private float attendance;

    public BusinessPerson(User user, int businessID, int NIP, String role, float rating, float attendance) {
        super(user.getId(), user.getName(), user.getEmail(), user.getPassword());
        this.businessID = businessID;
        this.NIP = NIP;
        this.role = role;
        this.rating = rating;
        this.attendance = attendance;
    }


    public int getNIP() {
        return NIP;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
    }

    public float getRating() {
        if (!Objects.equals(role, "Seller")){
            throw new RuntimeException("You're not seller");
        }
        return rating;
    }

    public void setRating(float rating) {
        if (!Objects.equals(role, "Seller")){
            throw new RuntimeException("You're not seller");
        }
        this.rating = rating;
    }

    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }

    public float getAttendance(){
        if (!Objects.equals(role, "Organizer")){
            throw new RuntimeException("You're not organizer");
        }
        return attendance;
    }

    public void setAttendance(float attendance)  {
        if (!Objects.equals(role, "Organizer")){
            throw new RuntimeException("You're not organizer");
        }
        this.attendance = attendance;
    }
}
