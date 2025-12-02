package com.example.demo.model.notForGUI;

import java.time.LocalDate;

public class Event {
    private int id;
    private int businessPersonId;
    private String description;
    private LocalDate dateTime;
    private String location;
    private int capacity;

    public static int maxCapacity = 200;

    public Event(int id, int businessPersonId, String description, LocalDate dateTime, String location, int capacity) throws Exception {
        if (capacity>maxCapacity){
            throw new Exception("Too many people");
        }
        this.id = id;
        this.businessPersonId = businessPersonId;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
    }
}
