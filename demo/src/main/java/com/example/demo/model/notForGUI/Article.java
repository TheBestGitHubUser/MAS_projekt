package com.example.demo.model.notForGUI;

import java.time.LocalDate;

public class Article {
    private int id;
    private int employeeId;
    private String title;
    private String content;
    private LocalDate publishDate;
    private int views;

    public Article(int id, int employeeId, String title, String content, LocalDate publishDate, int views) {
        this.id = id;
        this.employeeId = employeeId;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.views = views;
    }
}
