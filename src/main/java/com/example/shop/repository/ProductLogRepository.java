package com.example.shop.repository;

import com.example.shop.model.ProductLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductLogRepository extends MongoRepository<ProductLog, String> {
    List<ProductLog> findByProductIdOrderByPerformedAtDesc(String productId);
}
