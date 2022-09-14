package com.extoxesses.flowershop.utility;

import com.extoxesses.flowershop.dto.OrderDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * This method was created to "normalize" the input:
     * requirements not specify if is possible to have many rows per flower
     *
     * @param details Order details
     * @return the normalized list of flower
     */
    public static List<OrderDetails> normalizeInput(Collection<OrderDetails> details) {
        return details.stream()
                .collect(Collectors.groupingBy(d -> d.getFlowerCode()))
                .values()
                .stream()
                .map(Mapper::reduceCallback)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
     * Callback to reduce the list of request details into a single object
     *
     * @param details List of details related to the same flower
     * @return the reduced detail
     */
    private static OrderDetails reduceCallback(List<OrderDetails> details) {
        return details.stream()
                .reduce((a, b) -> {
                    a.setQantity(a.getQantity() + b.getQantity());
                    return a;
                }).get();
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
