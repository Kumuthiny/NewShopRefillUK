package com.example.shop;

import com.example.shop.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);

	@Autowired
	private ShopRepository shopRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Warm up MongoDB connection on startup
		try {
			long start = System.currentTimeMillis();
			shopRepository.count();
			long duration = System.currentTimeMillis() - start;
			log.info("MongoDB connection warmed up in {}ms", duration);
		} catch (Exception e) {
			log.warn("Failed to warm up MongoDB connection: {}", e.getMessage());
		}
	}
}