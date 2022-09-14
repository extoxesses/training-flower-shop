package com.extoxesses.flowershop.entities.keys;

import java.io.Serializable;
import java.util.Objects;

public class BundlePrimaryKey implements Serializable {

    private static final long serialVersionUID = -1012233070589337389L;

    private String flowerCode;
    private int size;

    public String getFlowerCode() {
        return flowerCode;
    }

    public void setFlowerCode(String flowerCode) {
        this.flowerCode = flowerCode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BundlePrimaryKey that = (BundlePrimaryKey) o;
        return size == that.size && Objects.equals(flowerCode, that.flowerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flowerCode, size);
    }

}
