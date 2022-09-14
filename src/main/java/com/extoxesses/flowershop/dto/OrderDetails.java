package com.extoxesses.flowershop.dto;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    private static final long serialVersionUID = -7809508104543339978L;

    private int quantity;
    private String flowerCode;

    public OrderDetails(int amount, String flowerCode) {
        this.quantity = amount;
        this.flowerCode = flowerCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFlowerCode() {
        return flowerCode;
    }

    public void setFlowerCode(String flowerCode) {
        this.flowerCode = flowerCode;
    }

}
