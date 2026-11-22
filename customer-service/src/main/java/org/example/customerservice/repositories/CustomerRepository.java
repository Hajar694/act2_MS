package org.example.customerservice.repositories;

import org.example.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// CustomerRepository.java
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
