package com.extoxesses.flowershop;

import com.extoxesses.flowershop.dto.OrderResponse;
import com.extoxesses.flowershop.entities.Flower;
import com.extoxesses.flowershop.repositories.FlowerRepository;
import com.extoxesses.flowershop.services.FlowerShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlowerShopServiceTest {

    @SpyBean
    private FlowerRepository flowerRepository;
    @Autowired
    @InjectMocks
    private FlowerShopService flowerService;

    @Test
    @Order(1)
    void makeOrderTest() {
        List<String> request = new ArrayList<>();
        request.add("10 R12");
        request.add("15 L09");
        request.add("13 T58");
        Map<String, Integer> expectedPrices = new HashMap<>();
        expectedPrices.put("R12", 1299);
        expectedPrices.put("L09", 4190);
        expectedPrices.put("T58", 2585);

        List<OrderResponse> response = flowerService.makeOrder(request, true);
        Assertions.assertEquals(3, response.size());
        response.forEach(r -> {
            Integer expected = expectedPrices.get(r.getFlowerCode());
            Assertions.assertNotNull(expected, r.getFlowerCode());
            Assertions.assertEquals(expected, (int) (r.getPrice() * 100), r.getFlowerCode());
        });
    }

    @Test
    @Order(2)
    void findAllTest() {
        Flower mockFlower = new Flower();
        mockFlower.setCode("code");
        List<Flower> mockDb = Collections.singletonList(mockFlower);
        Mockito.when(flowerRepository.findAll())
                .thenReturn(mockDb);

        Assertions.assertEquals(mockDb, flowerService.findAll());
    }

}
