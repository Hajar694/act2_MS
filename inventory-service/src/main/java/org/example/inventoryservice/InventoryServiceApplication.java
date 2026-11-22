package org.example.inventoryservice;

import org.example.inventoryservice.entities.Product;
import org.example.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository repo, RepositoryRestConfiguration conf) {
        return args -> {
            conf.exposeIdsFor(Product.class);
            repo.save(Product.builder().name("Ordinateur").price(3200).quantity(11).build());
            repo.save(Product.builder().name("Imprimante").price(1299).quantity(15).build());
            repo.save(Product.builder().name("Smartphone").price(5400).quantity(8).build());
        };
    }
}