package com.extoxesses.flowershop;

import com.extoxesses.flowershop.dto.OrderResponse;
import com.extoxesses.flowershop.entities.Flower;
import com.extoxesses.flowershop.repositories.FlowerRepository;
import com.extoxesses.flowershop.services.FlowerShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class FlowerShopServiceTest {

    @SpyBean
    private FlowerRepository flowerRepository;
    @Autowired
    @InjectMocks
    private FlowerShopService flowerService;

    /**
     * In this test I cover the example given with the problem
     */
    @Test
    void makeOrderTestBase() {
        List<String> request = new ArrayList<>();
        request.add("10 R12");
        request.add("15 L09");
        request.add("13 T58");

        Map<String, Integer> expectedPrices = new HashMap<>();
        expectedPrices.put("R12", 1299);
        expectedPrices.put("L09", 4190);
        expectedPrices.put("T58", 2585);

        List<OrderResponse> response = flowerService.makeOrder(request);
        Assertions.assertEquals(3, response.size());
        response.forEach(r -> {
            Integer expected = expectedPrices.get(r.getFlowerCode());
            Assertions.assertNotNull(expected, r.getFlowerCode());
            Assertions.assertEquals(expected, (int) (r.getPrice() * 100), r.getFlowerCode());
        });
    }

    @Test
    void makeOrderTestAdvance() {
        Map<String, Integer> request = new HashMap<>();
        request.put("15 R12", 1998);
        request.put("6 L09", 1695);
        request.put("9 L09", 2495);
        // NOTE: In this scenario the detected solution is not the cheapest one.
        // However, if we want to reach also this goal, we could update the makeOrder method in order to compute all the
        // possible solution, and then keep the solution with minimum cost
        request.put("12 L09", 3490);
        request.put("18 L09", 4990);

        request.entrySet().forEach(set -> {
            List<OrderResponse> response = flowerService.makeOrder(Collections.singletonList(set.getKey()));
            Assertions.assertEquals(1, response.size());

            String message = set.getKey() + " " + set.getValue();
            Assertions.assertEquals(set.getValue(), (int) (response.get(0).getPrice() * 100), message);
        });
    }

    @Test
    void makeOrderBadRequestTest() {
        List<String> request = new ArrayList<>();
        request.add("6 R12");
        request.add("11 R12");
        request.add("32 R12");
        request.add("4 L09");
        request.add("17 L09");

        request.forEach(r -> Assertions.assertThrows(ResponseStatusException.class,
                    () -> flowerService.makeOrder(Collections.singletonList(r)))
        );
    }

    @Test
    void findAllTest() {
        Flower mockFlower = new Flower();
        mockFlower.setCode("code");
        List<Flower> mockDb = Collections.singletonList(mockFlower);
        Mockito.when(flowerRepository.findAll())
                .thenReturn(mockDb);

        Assertions.assertEquals(mockDb, flowerService.findAll());
    }

}
