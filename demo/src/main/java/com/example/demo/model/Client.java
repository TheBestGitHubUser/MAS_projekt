package com.example.demo.model;

import java.time.LocalDate;

public class Client extends User{
    private int id;
    private int userId;
    private LocalDate birthDate;

    public Client(User user,int id, int userId, LocalDate birthDate) {
        super(user.getId(),user.getName(),user.getEmail(),user.getPassword());
        this.id = id;
        this.userId = userId;
        this.birthDate = birthDate;
    }
}
