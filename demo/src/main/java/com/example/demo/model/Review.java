package com.example.demo.model;

public class Review {
    private int id;
    private int orderId;
    private String comment;

    public Review(int id, int orderId, String comment) {
        this.id = id;
        this.orderId = orderId;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
