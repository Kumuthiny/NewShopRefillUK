package com.example.shop.service;

import com.example.shop.model.Shop;
import com.example.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final ShopRepository repo;

    public ShopService(ShopRepository repo) {
        this.repo = repo;
    }

    public Shop createShop(String name) {
        Shop s = new Shop();
        s.setName(name);
        return repo.save(s);
    }

    public List<Shop> getAllShops() {
        return repo.findAll();
    }

    public Shop getShopById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
    }
}
