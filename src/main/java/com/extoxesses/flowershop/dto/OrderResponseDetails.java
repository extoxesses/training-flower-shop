package com.extoxesses.flowershop.dto;

import java.io.Serializable;

public class OrderResponseDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private int amount;
    private int boundleSize;
    private double price;

    public OrderResponseDetails() {
        super();
    }

    public OrderResponseDetails(int amount, int boundleSize, double price) {
        this.amount = amount;
        this.boundleSize = boundleSize;
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBoundleSize() {
        return boundleSize;
    }

    public void setBoundleSize(int boundleSize) {
        this.boundleSize = boundleSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
