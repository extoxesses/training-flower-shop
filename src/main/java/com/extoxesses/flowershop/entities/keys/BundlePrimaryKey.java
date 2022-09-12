package com.extoxesses.flowershop.entities.keys;

import java.io.Serializable;
import java.util.Objects;

public class BundlePrimaryKey implements Serializable {

    private static final long serialVersionUID = -1012233070589337389L;

    private String flowerCode;
    private int amount;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BundlePrimaryKey that = (BundlePrimaryKey) o;
        return amount == that.amount && Objects.equals(flowerCode, that.flowerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flowerCode, amount);
    }

}
