package org.example.customerservice;

import org.example.customerservice.entities.Customer;
import org.example.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository repo, RepositoryRestConfiguration conf) {
        return args -> {
            conf.exposeIdsFor(Customer.class);
            repo.save(Customer.builder().name("Hassan").email("hassan@gmail.com").build());
            repo.save(Customer.builder().name("Mohamed").email("med@gmail.com").build());
            repo.save(Customer.builder().name("Imane").email("imane@gmail.com").build());
        };
    }
}