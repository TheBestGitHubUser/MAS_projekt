package com.example.demo.model.notForGUI;

import com.example.demo.model.User;

import java.time.LocalDate;

public class Employee extends User {
    private int id;
    private int userId;
    private String position;
    private LocalDate hireDate;
    private float salary;

    public Employee(User user,int id, int userId, String position, LocalDate hireDate, float salary) {
        super(user.getId(),user.getName(),user.getEmail(),user.getPassword());
        this.id = id;
        this.userId = userId;
        this.position = position;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
