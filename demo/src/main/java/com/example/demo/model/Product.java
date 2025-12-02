package com.example.demo.model;

public class Product {
    private int id;
    private int businessPersonId;
    private String name;
    private String description;
    private float price;
    private int stock;
    private String category;
    private String imageURL;

    public Product(int id, int businessPersonId, String name, String description, float price, int stock, String category, String imageURL) {
        this.id = id;
        this.businessPersonId = businessPersonId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBusinessPersonId() {
        return businessPersonId;
    }

    public void setBusinessPersonId(int businessPersonId) {
        this.businessPersonId = businessPersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
