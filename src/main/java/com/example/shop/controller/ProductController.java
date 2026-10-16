package com.example.shop.controller;

import com.example.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam String addedBy,
                      @RequestParam String shopId,
                      @RequestParam(required = false) String category,
                      @RequestParam(required = false) String details,
                      @RequestParam(required = false) MultipartFile image) throws IOException {

        String imageBase64 = null;
        String imageType = null;
        if (image != null && !image.isEmpty()) {
            imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
            imageType = image.getContentType();
        }
        service.addProduct(name, addedBy, shopId, category, details, imageBase64, imageType);
        return "redirect:/shop/" + shopId;
    }

    @PostMapping("/{id}/need")
    public String need(@PathVariable String id,
                       @RequestParam(required = false) Integer amount,
                       @RequestParam(required = false) String updatedBy) {
        var p = service.markNeedRefill(id, amount, updatedBy);
        return "redirect:/shop/" + p.getShopId();
    }

    @PostMapping("/{id}/edit-amount")
    public String editAmount(@PathVariable String id,
                             @RequestParam Integer amount,
                             @RequestParam(required = false) String updatedBy) {
        var p = service.editRefillAmount(id, amount, updatedBy);
        return "redirect:/shop/" + p.getShopId();
    }

    @PostMapping("/{id}/done")
    public String done(@PathVariable String id,
                       @RequestParam(required = false) String updatedBy) {
        var p = service.markRefilled(id, updatedBy);
        return "redirect:/shop/" + p.getShopId();
    }

    @GetMapping("/{id}/history")
    public String history(@PathVariable String id, Model model) {
        var p = service.getProductById(id);
        model.addAttribute("product", p);
        model.addAttribute("logs", service.getLogsForProduct(id));
        return "product-history";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        var p = service.getProductById(id);
        service.deleteProduct(id);
        return "redirect:/shop/" + p.getShopId();
    }
}
