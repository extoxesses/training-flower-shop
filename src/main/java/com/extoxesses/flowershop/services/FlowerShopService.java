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
     * @param order       The order, as a list of string with format '<quantity> <flower_core>'
     *                    (as required by the exercise)
     * @param onlyBundles As the requirements don't specify what is the expected behaviour in the case the input doesn't matches
     *                    with the bundles sizes, I added this field to control two different behaviours
     * @return the final order
     */
    public List<OrderResponse> makeOrder(List<String> order, boolean onlyBundles) {
        // Normalize input, to simplify bundle estimation
        List<OrderDetails> normalizedDetails = normalizeInput(Mapper.parseRequest(order));

        // Retrieves all required bundles (accordingly with the input)
        Map<String, List<Bundle>> bundles = getBundlesByFlower(normalizedDetails);

        List<OrderResponse> response = new ArrayList<>();
        for (OrderDetails detail : normalizedDetails) {
            List<Bundle> flowerBundles = bundles.get(detail.getFlowerCode());
            if (Objects.isNull(flowerBundles)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flower " + detail.getFlowerCode() + "not found");
            }

            List<OrderResponseDetails> responseDetails = computeBundles(detail.getAmount(), flowerBundles);
            double price = responseDetails.stream()
                    .map(rd -> rd.getQuantity() * rd.getPrice())
                    .reduce((a, b) -> a + b)
                    .orElse(0.0);
            response.add(new OrderResponse(detail.getAmount(), detail.getFlowerCode(), price, responseDetails));
        }
        return response;
    }

    // -- Private methods

    private List<OrderResponseDetails> computeBundles(int size, List<Bundle> bundles) {
        if (bundles.size() == 0) {
            // If there aren't any bundles, any check is required
            return Collections.emptyList();
        }

        List<OrderResponseDetails> winnerBundles = new ArrayList<>();
        for (int i = 0; i < bundles.size(); ++i) {
            Bundle bundle = bundles.get(i);
            if (size < bundle.getSize()) {
                // If bundle size is higher than size, ignore that bundle
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
