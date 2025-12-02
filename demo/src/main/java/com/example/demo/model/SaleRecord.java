package com.example.demo.model;

import java.time.LocalDate;

public class SaleRecord {

    private int orderID;
    private String clientName;
    private String clientEmail;
    private String productName;
    private float productPrice;
    private LocalDate orderDate;
    private String status;

    public SaleRecord(int orderID, String clientName, String clientEmail, String productName, float productPrice, LocalDate orderDate, String status) {
        this.orderID = orderID;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
