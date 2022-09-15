package com.extoxesses.flowershop.services;

import com.extoxesses.flowershop.dto.OrderDetails;
import com.extoxesses.flowershop.dto.OrderResponse;
import com.extoxesses.flowershop.dto.OrderResponseDetails;
import com.extoxesses.flowershop.entities.Bundle;
import com.extoxesses.flowershop.entities.Flower;
import com.extoxesses.flowershop.repositories.FlowerRepository;
import com.extoxesses.flowershop.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlowerShopService {

    @Autowired
    private FlowerRepository flowerRepository;

    /**
     * Method to retrieve all flower from the database.
     * In the exercise scope, it was created mainly for debug purposes.
     *
     * @return the list of all flowers (with their bundles)
     */
    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }

    /**
     * Method that implements the exercise logic.
     *
     * @param order The order, as a list of string with format '<quantity> <flower_core>'
     *              (as required by the exercise)
     * @return the final order
     */
    public List<OrderResponse> makeOrder(List<String> order) {
        // In the requirements there isn't any information about orders with multiple request of the same flower.
        // I supposed to "normalize" the input in order to optimize the bundles
        List<OrderDetails> normalizedDetails = Mapper.normalizeInput(Mapper.parseRequest(order));

        // Retrieves all required bundles (accordingly with the input)
        Map<String, List<Bundle>> bundles = getBundlesByFlower(normalizedDetails);

        // Execute the real business logic
        List<OrderResponse> response = new ArrayList<>();
        for (OrderDetails detail : normalizedDetails) {
            List<Bundle> flowerBundles = bundles.get(detail.getFlowerCode());
            if (Objects.isNull(flowerBundles)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flower " + detail.getFlowerCode() + "not found");
            }

            List<OrderResponseDetails> responseDetails = computeBundles(detail.getQuantity(), flowerBundles);
            double price = responseDetails.stream()
                    .map(rd -> rd.getQuantity() * rd.getPrice())
                    .reduce(Double::sum)
                    .orElse(0.0);
            response.add(new OrderResponse(detail.getQuantity(), detail.getFlowerCode(), price, responseDetails));
        }

        if (response.isEmpty() || response.get(0).getDetails().isEmpty()) {
            // Since, there aren't any requirements that specify what to do if is impossible to estimate a minimum order
            // I throw an exception. However, this not affect the algorithm that compute the order itself
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to compute bundle");
        }

        return response;
    }

    // -- Private methods

    /**
     * makeOrder recursive component
     *
     * @param size    Order size
     * @param bundles Available bundles
     * @return the estimated order
     */
    private List<OrderResponseDetails> computeBundles(int size, List<Bundle> bundles) {
        // If there aren't any bundles, any check is required
        if (bundles.size() == 0) {
            return Collections.emptyList();
        }

        List<OrderResponseDetails> winnerBundles = new ArrayList<>();
        for (int i = 0; i < bundles.size(); ++i) {
            Bundle bundle = bundles.get(i);
            // If bundle size is higher than size, ignore that bundle
            if (size < bundle.getSize()) {
                continue;
            }

            int quantity = size / bundle.getSize();
            int remains = size % bundle.getSize();
            OrderResponseDetails ord = new OrderResponseDetails(quantity, bundle);

            if (remains == 0) {
                // In this case, we have found the last bundle
                return Collections.singletonList(ord);
            } else {
                // Otherwise, continue with the analysis, and save bundles only if we found the correct distribution
                List<OrderResponseDetails> e = computeBundles(remains, bundles.subList(i + 1, bundles.size()));
                if (!e.isEmpty()) {
                    winnerBundles.addAll(e);
                    winnerBundles.add(ord);
                    break;
                }
            }
        }

        return winnerBundles;
    }

    /**
     * Retrieves the bundle lookup matrix
     *
     * @param details Order details
     * @return the bundle lookup matrix, with flower_code as key
     */
    private Map<String, List<Bundle>> getBundlesByFlower(List<OrderDetails> details) {
        List<String> requiredFlowers = details.stream()
                .map(OrderDetails::getFlowerCode)
                .collect(Collectors.toList());

        return flowerRepository.findAllByCodeIn(requiredFlowers)
                .stream()
                .peek(b -> b.getBundles().sort(Collections.reverseOrder()))
                .collect(Collectors.toMap(Flower::getCode, Flower::getBundles));
    }

}
