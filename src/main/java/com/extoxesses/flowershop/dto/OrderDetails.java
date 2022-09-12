package com.extoxesses.flowershop.dto;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private int amount;
    private String flowerCode;

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
