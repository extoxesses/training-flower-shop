package com.extoxesses.flowershop.dto;

import com.extoxesses.flowershop.entities.Bundle;

import java.io.Serializable;

public class OrderResponseDetails implements Serializable {

    private static final long serialVersionUID = 4946347329643186529L;

    private int quantity;
    private int size;
    private double price;

    public OrderResponseDetails() {
        super();
    }

    public OrderResponseDetails(int amount, int size, double price) {
        this.quantity = amount;
        this.size = size;
        this.price = price;
    }

    public OrderResponseDetails(int amount, Bundle bundle) {
        this.quantity = amount;
        this.size = bundle.getSize();
        this.price = bundle.getPrice() / 100.0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
