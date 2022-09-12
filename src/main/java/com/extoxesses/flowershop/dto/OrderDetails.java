package com.extoxesses.flowershop.dto;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    private static final long serialVersionUID = -7809508104543339978L;

    private int amount;
    private String flowerCode;

    public OrderDetails(int amount, String flowerCode) {
        this.amount = amount;
        this.flowerCode = flowerCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFlowerCode() {
        return flowerCode;
    }

    public void setFlowerCode(String flowerCode) {
        this.flowerCode = flowerCode;
    }

}
