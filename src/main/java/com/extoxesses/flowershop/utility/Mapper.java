package com.extoxesses.flowershop.utility;

import com.extoxesses.flowershop.dto.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    /**
     * Converts requirements inputs into a manipulable object
     *
     * @param order Input order
     * @return manipulable object
     */
    public static List<OrderDetails> parseRequest(List<String> order) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for (String row : order) {
            orderDetails.add(rowToDetail(row));
        }
        return orderDetails;
    }

    // -- Private methods

    /**
     * Utility class private constructor
     *
     * @throws IllegalAccessException if invoked
     */
    private Mapper() throws IllegalAccessException {
        throw new IllegalAccessException("Invalid utility class constructor");
    }

    /**
     * Converts input into an object
     *
     * @param row Input row
     * @return mapped object
     */
    private static OrderDetails rowToDetail(String row) {
        String[] elems = row.split(" ");
        int quantity = Integer.parseInt(elems[0]);
        return new OrderDetails(quantity, elems[1]);
    }

}
