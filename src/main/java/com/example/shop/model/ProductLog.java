package com.example.shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "product_logs")
public class ProductLog {

    @Id
    private String id;

    private String productId;
    private String productName;
    private String shopName;

    // ADDED, NEED_REFILL, AMOUNT_UPDATED, REFILLED
    private String action;

    private String performedBy;
    private LocalDateTime performedAt;
    private Integer refillAmount;

    public String getId() { return id; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getShopName() { return shopName; }
    public String getAction() { return action; }
    public String getPerformedBy() { return performedBy; }
    public LocalDateTime getPerformedAt() { return performedAt; }
    public Integer getRefillAmount() { return refillAmount; }

    public void setId(String id) { this.id = id; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public void setAction(String action) { this.action = action; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
    public void setPerformedAt(LocalDateTime performedAt) { this.performedAt = performedAt; }
    public void setRefillAmount(Integer refillAmount) { this.refillAmount = refillAmount; }
}
