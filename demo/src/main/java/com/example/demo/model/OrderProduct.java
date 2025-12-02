package com.example.demo.model;

import java.time.LocalDate;

public class OrderProduct {
    private int id;
    private int clientId;
    private int productId;
    private LocalDate orderDate;
    private String status;

    public OrderProduct(int id, int clientId, int productId, LocalDate orderDate, String status) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.status = status;
    }
}
