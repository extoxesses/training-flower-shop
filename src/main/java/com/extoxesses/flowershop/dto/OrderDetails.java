package com.extoxesses.flowershop.dto;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    private static final long serialVersionUID = -7809508104543339978L;

    private int qantity;
    private String flowerCode;

    public OrderDetails(int amount, String flowerCode) {
        this.qantity = amount;
        this.flowerCode = flowerCode;
    }

    public int getQantity() {
        return qantity;
    }

    public void setQantity(int qantity) {
        this.qantity = qantity;
    }

    public String getFlowerCode() {
        return flowerCode;
    }

    public void setFlowerCode(String flowerCode) {
        this.flowerCode = flowerCode;
    }

}
