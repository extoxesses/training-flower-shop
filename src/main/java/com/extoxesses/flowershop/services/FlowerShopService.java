package com.extoxesses.flowershop.services;

import com.extoxesses.flowershop.dto.Order;
import com.extoxesses.flowershop.dto.OrderDetails;
import com.extoxesses.flowershop.dto.OrderResponse;
import com.extoxesses.flowershop.dto.OrderResponseDetails;
import com.extoxesses.flowershop.entities.Bundle;
import com.extoxesses.flowershop.entities.Flower;
import com.extoxesses.flowershop.repositories.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlowerShopService {

    @Autowired
    private FlowerRepository flowerRepository;

    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }

    public List<Flower> findByCodesIn(List<String> flowerCodes) {
        return flowerRepository.findAllByCodeIn(flowerCodes);
    }

    public List<OrderResponse> makeOrder(Order order) {
        // Normalize input, to simplify bundle estimation
        List<OrderDetails> normalizedDetails = normalizeInput(order.getDetails());

        // Retrieves all required bundles (accordingly with the input)
        Map<String, List<Bundle>> bundles = getBundlesByFlower(normalizedDetails);

        // Estimate the final bundles
        return normalizedDetails.stream()
                .map(detail -> computeBundles(detail, bundles.get(detail.getFlowerCode())))
                .collect(Collectors.toList());
    }

    // -- Private methods
//
    private OrderResponse computeBundles(OrderDetails detail, List<Bundle> bundles) {
        int remain = detail.getAmount();
        OrderResponse order = new OrderResponse(detail.getAmount(), detail.getFlowerCode(), 0.0, new ArrayList<>());

        // TODO: rivedere i nomi
        for(Bundle b : bundles) {
            if (remain >= b.getAmount()) {
                int amount = remain / b.getAmount();
                remain %= b.getAmount();
                order.getDetails().add(new OrderResponseDetails(amount, b.getAmount(), b.getPrice() / 100.0));
                order.setPrice(order.getPrice() + (amount * b.getPrice() / 100.0));
            }
        }

        if (remain != 0) {
            String errMsg = "Sorry, your order can't be satisfy. Add another " + " flowers to your order or remove " + " to complete your order";
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, errMsg);
        }
        return order;
    }

    private Map<String, List<Bundle>> getBundlesByFlower(List<OrderDetails> details) {
        List<String> requiredFlowers = details.stream()
                .map(OrderDetails::getFlowerCode)
                .collect(Collectors.toList());

        Map<String, List<Bundle>> bundles = flowerRepository.findAllByCodeIn(requiredFlowers)
                .stream()
                .collect(Collectors.toMap(Flower::getCode, Flower::getBundles));

        bundles.values().forEach(b -> Collections.sort(b, Collections.reverseOrder()));

        return bundles;
    }

    /**
     * This method was created to "normalize" the input:
     * requirements not specify if is possible to have many rows per flower
     *
     * @param details Order details
     * @return the normalized list of flower
     */
    private List<OrderDetails> normalizeInput(Collection<OrderDetails> details) {
        return details.stream()
                .collect(Collectors.groupingBy(d -> d.getFlowerCode()))
                .values()
                .stream()
                .map(this::reduceCallback)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Callback to reduce the list of request details into a single object
     *
     * @param details List of details related to the same flower
     * @return the reduced detail
     */
    private OrderDetails reduceCallback(List<OrderDetails> details) {
        return details.stream()
                .reduce((a, b) -> {
                    a.setAmount(a.getAmount() + b.getAmount());
                    return a;
                }).get();
    }

}
