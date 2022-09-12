package com.extoxesses.flowershop.entities;

import com.extoxesses.flowershop.entities.keys.BundlePrimaryKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "bundle")
@IdClass(BundlePrimaryKey.class)
public class Bundle implements Comparable<Bundle>, Serializable {

    private static final long serialVersionUID = 5284711218358784875L;

    @Id
    private String flowerCode;
    @Id
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private int price;

    public String getFlowerCode() {
        return flowerCode;
    }

    public void setFlowerCode(String flowerCode) {
        this.flowerCode = flowerCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int compareTo(Bundle that) {
        return this.getAmount() - that.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bundle bundle = (Bundle) o;
        return amount == bundle.amount && Objects.equals(flowerCode, bundle.flowerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flowerCode, amount);
    }

}
