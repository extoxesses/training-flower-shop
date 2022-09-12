package com.extoxesses.flowershop.dto;

import java.io.Serializable;
import java.util.List;

public class OrderResponse implements Serializable {

    private static final long serialVersionUID = -6453948692648958711L;

    private int totalAmount;
    private String flowerCode;
    private double price;
    private List<OrderResponseDetails> details;

    public OrderResponse() {
        super();
    }

    public OrderResponse(int totalAmount, String flowerCode, double price, List<OrderResponseDetails> details) {
        this.totalAmount = totalAmount;
        this.flowerCode = flowerCode;
        this.price = price;
        this.details = details;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFlowerCode() {
        return flowerCode;
    }

    public void setFlowerCode(String flowerCode) {
        this.flowerCode = flowerCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<OrderResponseDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderResponseDetails> details) {
        this.details = details;
    }

}
