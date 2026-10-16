package com.example.shop.dto;

import com.example.shop.model.Product;

import java.util.List;
import java.util.Map;

public record RefillStats(
        List<Product> refillProducts,
        int totalQuantity,
        long shopCount,
        Map<String, Integer> shopRefillMap,
        Map<String, List<Product>> refillByShop
) {}
