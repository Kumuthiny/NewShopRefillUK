package com.example.shop.controller;

import com.example.shop.service.ProductService;
import com.example.shop.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShopController {

    private static final Logger log = LoggerFactory.getLogger(ShopController.class);
    private final ShopService shopService;
    private final ProductService productService;

    public ShopController(ShopService shopService, ProductService productService) {
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        long startTime = System.currentTimeMillis();
        try {
            long t1 = System.currentTimeMillis();
            model.addAttribute("shops", shopService.getAllShops());
            log.info("getAllShops took {}ms", System.currentTimeMillis() - t1);

            long t2 = System.currentTimeMillis();
            var stats = productService.getRefillStats();
            log.info("getRefillStats took {}ms", System.currentTimeMillis() - t2);

            model.addAttribute("refillProducts", stats.refillProducts());
            model.addAttribute("shopCount", stats.shopCount());
            model.addAttribute("totalQuantity", stats.totalQuantity());
            model.addAttribute("shopRefillMap", stats.shopRefillMap());
            model.addAttribute("refillByShop", stats.refillByShop());
            model.addAttribute("totalShops", shopService.getAllShops().size());
        } catch (Exception e) {
            model.addAttribute("shops", java.util.Collections.emptyList());
            model.addAttribute("refillProducts", java.util.Collections.emptyList());
            model.addAttribute("shopCount", 0);
            model.addAttribute("totalQuantity", 0);
            model.addAttribute("shopRefillMap", java.util.Collections.emptyMap());
            model.addAttribute("refillByShop", java.util.Collections.emptyMap());
            model.addAttribute("totalShops", 0);
        }
        long total = System.currentTimeMillis() - startTime;
        log.info("Home page total: {}ms", total);
        return "index";
    }

    @GetMapping("/shop/{id}")
    public String shop(@PathVariable String id, Model model) {
        model.addAttribute("shop", shopService.getShopById(id));
        model.addAttribute("products", productService.getProductsByShop(id));
        return "shop";
    }

    @PostMapping("/shops/add")
    public String addShop(@RequestParam String name) {
        shopService.createShop(name);
        return "redirect:/";
    }
}
