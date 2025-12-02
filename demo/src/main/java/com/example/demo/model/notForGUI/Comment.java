package com.example.demo.model.notForGUI;

import java.time.LocalDate;

public class Comment {
    private int id;
    private int clientId;
    private int articleId;
    private String content;
    private LocalDate postDate;

    public Comment(int id, int clientId, int articleId, String content, LocalDate postDate) {
        this.id = id;
        this.clientId = clientId;
        this.articleId = articleId;
        this.content = content;
        this.postDate = postDate;
    }
}
