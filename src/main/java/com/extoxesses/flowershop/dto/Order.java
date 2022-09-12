package com.extoxesses.flowershop.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<OrderDetails> details;

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;
        return Objects.equals(details, order.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details);
    }

}
