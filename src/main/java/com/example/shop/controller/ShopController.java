package com.example.shop.controller;

import com.example.shop.service.ProductService;
import com.example.shop.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShopController {

    private final ShopService shopService;
    private final ProductService productService;

    public ShopController(ShopService shopService, ProductService productService) {
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("shops", shopService.getAllShops());
        model.addAttribute("refillProducts", productService.getRefillProducts());
        model.addAttribute("shopCount", productService.countShopsWithRefill());
        model.addAttribute("totalQuantity", productService.getTotalRefillQuantity());
        model.addAttribute("shopRefillMap", productService.getShopRefillMap());
        model.addAttribute("refillByShop", productService.getRefillProductsByShop());
        model.addAttribute("totalShops", shopService.getAllShops().size());
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
