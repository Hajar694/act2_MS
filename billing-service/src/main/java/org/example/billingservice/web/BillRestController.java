package org.example.billingservice.web;

import org.example.billingservice.entities.Bill;
import org.example.billingservice.feign.CustomerRestClient;
import org.example.billingservice.feign.ProductRestClient;
import org.example.billingservice.repositories.BillRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillRestController {

    private final BillRepository billRepository;
    private final CustomerRestClient customerClient;
    private final ProductRestClient productClient;

    public BillRestController(BillRepository billRepository,
                              CustomerRestClient customerClient,
                              ProductRestClient productClient) {
        this.billRepository = billRepository;
        this.customerClient = customerClient;
        this.productClient = productClient;
    }

    private Bill enrich(Bill bill) {
        bill.setCustomer(customerClient.findCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi ->
                pi.setProduct(productClient.findProductById(pi.getProductId())));
        return bill;
    }

    @GetMapping("/fullBill/{id}")
    public Bill getBill(@PathVariable Long id) {
        return enrich(billRepository.findById(id).orElseThrow());
    }

    @GetMapping("/customerBills/{customerId}")
    public List<Bill> getCustomerBills(@PathVariable Long customerId) {
        return billRepository.findByCustomerId(customerId).stream()
                .map(this::enrich)
                .toList();
    }
}