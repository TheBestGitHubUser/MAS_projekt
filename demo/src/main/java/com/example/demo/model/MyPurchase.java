package com.example.demo.model;

import java.time.LocalDate;

public class MyPurchase {


    private int orderID;
    private int productID;
    private String sellerName;
    private String productName;
    private LocalDate orderDate;
    private String status;
    private String imageURL;

    public MyPurchase(int orderID, int productID, String sellerName, String productName, LocalDate orderDate, String status, String imageURL) {
        this.orderID = orderID;
        this.productID = productID;
        this.sellerName = sellerName;
        this.productName = productName;
        this.orderDate = orderDate;
        this.status = status;
        this.imageURL = imageURL;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
