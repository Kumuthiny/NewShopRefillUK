package com.example.shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private String category;
    private String details; // optional notes about the product

    private String addedBy;
    private LocalDateTime addedTime;

    private String updatedBy;
    private LocalDateTime updatedTime;

    @Indexed
    private String status; // NORMAL / NEED_REFILL
    private Integer refillAmount;

    // image stored as base64
    private String imageBase64;
    private String imageType; // e.g. image/jpeg

    @Indexed
    private String shopId;
    private String shopName;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDetails() { return details; }
    public String getAddedBy() { return addedBy; }
    public LocalDateTime getAddedTime() { return addedTime; }
    public String getUpdatedBy() { return updatedBy; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public String getStatus() { return status; }
    public Integer getRefillAmount() { return refillAmount; }
    public String getShopId() { return shopId; }
    public String getShopName() { return shopName; }
    public String getImageBase64() { return imageBase64; }
    public String getImageType() { return imageType; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setDetails(String details) { this.details = details; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }
    public void setAddedTime(LocalDateTime addedTime) { this.addedTime = addedTime; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
    public void setStatus(String status) { this.status = status; }
    public void setRefillAmount(Integer refillAmount) { this.refillAmount = refillAmount; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    public void setImageType(String imageType) { this.imageType = imageType; }
}
