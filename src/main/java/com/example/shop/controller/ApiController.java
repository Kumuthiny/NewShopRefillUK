package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.model.ProductLog;
import com.example.shop.model.Shop;
import com.example.shop.service.ProductService;
import com.example.shop.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    private final ShopService shopService;
    private final ProductService productService;

    public ApiController(ShopService shopService, ProductService productService) {
        this.shopService = shopService;
        this.productService = productService;
    }

    // SHOPS
    @GetMapping("/shops")
    public List<Shop> getShops() {
        return shopService.getAllShops();
    }

    @PostMapping("/shops")
    public Shop addShop(@RequestBody Map<String, String> body) {
        return shopService.createShop(body.get("name"));
    }

    // PRODUCTS
    @GetMapping("/shops/{shopId}/products")
    public List<Product> getProducts(@PathVariable String shopId) {
        return productService.getProductsByShop(shopId);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Map<String, String> body) {
        return productService.addProduct(
                body.get("name"),
                body.get("addedBy"),
                body.get("shopId"),
                body.get("category")
        );
    }

    @PostMapping("/products/{id}/need")
    public Product needRefill(@PathVariable String id, @RequestBody Map<String, String> body) {
        Integer amount = body.get("amount") != null ? Integer.parseInt(body.get("amount")) : 1;
        return productService.markNeedRefill(id, amount, body.get("updatedBy"));
    }

    @PostMapping("/products/{id}/edit-amount")
    public Product editAmount(@PathVariable String id, @RequestBody Map<String, String> body) {
        Integer amount = body.get("amount") != null ? Integer.parseInt(body.get("amount")) : 1;
        return productService.editRefillAmount(id, amount, body.get("updatedBy"));
    }

    @PostMapping("/products/{id}/done")
    public Product markDone(@PathVariable String id, @RequestBody Map<String, String> body) {
        return productService.markRefilled(id, body.get("updatedBy"));
    }

    @DeleteMapping("/products/{id}")
    public Map<String, String> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return Map.of("status", "deleted");
    }

    // HISTORY
    @GetMapping("/products/{id}/history")
    public List<ProductLog> getHistory(@PathVariable String id) {
        return productService.getLogsForProduct(id);
    }

    // DASHBOARD SUMMARY
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return Map.of(
                "totalShops", shopService.getAllShops().size(),
                "shopsNeedRefill", productService.countShopsWithRefill(),
                "totalRefillItems", productService.getTotalRefillQuantity(),
                "refillProducts", productService.getRefillProducts()
        );
    }
}
