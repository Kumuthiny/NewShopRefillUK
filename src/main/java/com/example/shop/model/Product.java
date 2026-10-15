package com.example.shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private String category;

    private String addedBy;
    private LocalDateTime addedTime;

    private String updatedBy;
    private LocalDateTime updatedTime;

    private String status; // NORMAL / NEED_REFILL
    private Integer refillAmount;

    // store shop reference as id + name (no JPA join in MongoDB)
    private String shopId;
    private String shopName;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getAddedBy() { return addedBy; }
    public LocalDateTime getAddedTime() { return addedTime; }
    public String getUpdatedBy() { return updatedBy; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public String getStatus() { return status; }
    public Integer getRefillAmount() { return refillAmount; }
    public String getShopId() { return shopId; }
    public String getShopName() { return shopName; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }
    public void setAddedTime(LocalDateTime addedTime) { this.addedTime = addedTime; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
    public void setStatus(String status) { this.status = status; }
    public void setRefillAmount(Integer refillAmount) { this.refillAmount = refillAmount; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    public void setShopName(String shopName) { this.shopName = shopName; }
}
