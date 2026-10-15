package com.example.shop.service;

import com.example.shop.model.Product;
import com.example.shop.model.ProductLog;
import com.example.shop.model.Shop;
import com.example.shop.repository.ProductLogRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final ShopRepository shopRepo;
    private final ProductLogRepository logRepo;

    public ProductService(ProductRepository productRepo, ShopRepository shopRepo, ProductLogRepository logRepo) {
        this.productRepo = productRepo;
        this.shopRepo = shopRepo;
        this.logRepo = logRepo;
    }

    private String sanitize(String name) {
        return name != null && !name.trim().isEmpty() ? name.trim() : "Unknown";
    }

    private void log(Product p, String action, String performedBy, Integer amount) {
        ProductLog log = new ProductLog();
        log.setProductId(p.getId());
        log.setProductName(p.getName());
        log.setShopName(p.getShopName());
        log.setAction(action);
        log.setPerformedBy(sanitize(performedBy));
        log.setPerformedAt(LocalDateTime.now());
        log.setRefillAmount(amount);
        logRepo.save(log);
    }

    public Product addProduct(String name, String addedBy, String shopId, String category) {
        Shop shop = shopRepo.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        Product p = new Product();
        p.setName(name);
        p.setAddedBy(sanitize(addedBy));
        p.setAddedTime(LocalDateTime.now());
        p.setStatus("NORMAL");
        p.setShopId(shop.getId());
        p.setShopName(shop.getName());
        if (category != null && !category.trim().isEmpty()) {
            p.setCategory(category.trim());
        }
        productRepo.save(p);
        log(p, "ADDED", addedBy, null);
        return p;
    }

    public Product markNeedRefill(String id, Integer amount, String updatedBy) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int qty = amount != null && amount > 0 ? amount : 1;
        p.setStatus("NEED_REFILL");
        p.setRefillAmount(qty);
        p.setUpdatedBy(sanitize(updatedBy));
        p.setUpdatedTime(LocalDateTime.now());
        productRepo.save(p);
        log(p, "NEED_REFILL", updatedBy, qty);
        return p;
    }

    public Product editRefillAmount(String id, Integer amount, String updatedBy) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int qty = amount != null && amount > 0 ? amount : (p.getRefillAmount() != null ? p.getRefillAmount() : 1);
        p.setRefillAmount(qty);
        p.setUpdatedBy(sanitize(updatedBy));
        p.setUpdatedTime(LocalDateTime.now());
        productRepo.save(p);
        log(p, "AMOUNT_UPDATED", updatedBy, qty);
        return p;
    }

    public Product markRefilled(String id, String updatedBy) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        log(p, "REFILLED", updatedBy, p.getRefillAmount());
        p.setStatus("NORMAL");
        p.setRefillAmount(null);
        p.setUpdatedBy(sanitize(updatedBy));
        p.setUpdatedTime(LocalDateTime.now());
        return productRepo.save(p);
    }

    public Product getProductById(String id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductLog> getLogsForProduct(String productId) {
        return logRepo.findByProductIdOrderByPerformedAtDesc(productId);
    }

    public List<Product> getRefillProducts() {
        return productRepo.findByStatus("NEED_REFILL");
    }

    public List<Product> getProductsByShop(String shopId) {
        return productRepo.findByShopId(shopId);
    }

    public void deleteProduct(String id) {
        logRepo.findByProductIdOrderByPerformedAtDesc(id).forEach(l -> logRepo.delete(l));
        productRepo.deleteById(id);
    }

    public int getTotalRefillQuantity() {
        // count of distinct products needing refill (not sum of quantities)
        return (int) productRepo.findByStatus("NEED_REFILL").stream().count();
    }

    public long countShopsWithRefill() {
        return productRepo.findByStatus("NEED_REFILL")
                .stream()
                .map(Product::getShopId)
                .distinct()
                .count();
    }

    public Map<String, Integer> getShopRefillMap() {
        // count of products needing refill per shop (not sum of quantities)
        return productRepo.findByStatus("NEED_REFILL")
                .stream()
                .filter(p -> p.getShopId() != null)
                .collect(Collectors.groupingBy(
                        Product::getShopId,
                        Collectors.summingInt(p -> 1)
                ));
    }

    // pass refill products grouped by shop to the template
    public Map<String, List<Product>> getRefillProductsByShop() {
        return productRepo.findByStatus("NEED_REFILL")
                .stream()
                .filter(p -> p.getShopId() != null)
                .collect(Collectors.groupingBy(Product::getShopId));
    }
}
