package org.example.billingservice;

import org.example.billingservice.entities.Bill;
import org.example.billingservice.entities.ProductItem;
import org.example.billingservice.feign.CustomerRestClient;
import org.example.billingservice.feign.ProductRestClient;
import org.example.billingservice.model.Customer;
import org.example.billingservice.repositories.BillRepository;
import org.example.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepo,
                            ProductItemRepository itemRepo,
                            CustomerRestClient customerClient,
                            ProductRestClient productClient) {
        return args -> {
            Customer customer = customerClient.findCustomerById(1L);
            Bill bill = billRepo.save(new Bill(null, new Date(), customer.getId(), null, null));

            productClient.findAllProducts().getContent().forEach(p -> {
                ProductItem item = new ProductItem();
                item.setBill(bill);
                item.setProductId(p.getId());
                item.setQuantity(1 + new Random().nextInt(10));
                item.setPrice(p.getPrice());
                item.setDiscount(Math.random());
                itemRepo.save(item);
            });
        };
    }
}