package com.extoxesses.flowershop.controllers;

import com.extoxesses.flowershop.constants.PathConstants;
import com.extoxesses.flowershop.dto.OrderResponse;
import com.extoxesses.flowershop.entities.Flower;
import com.extoxesses.flowershop.services.FlowerShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(PathConstants.BASE_PATH)
public class FlowerShopController {

    @Autowired
    private FlowerShopService shopService;

    @GetMapping
    public ResponseEntity<List<Flower>> getAvailableBundles() {
        List<Flower> flowers = shopService.findAll();
        return ResponseEntity.ok(flowers);
    }

    @PostMapping
    public ResponseEntity<List<OrderResponse>> makeOrder(@RequestBody List<String> order) {
        List<OrderResponse> response = shopService.makeOrder(order, true);
        return ResponseEntity.ok(response);
    }

}
