package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.CustomerDTO;
import com.minorproject.inventory.entity.Customer;
import com.minorproject.inventory.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepo;
    
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = Customer.builder()
                .name(customerDTO.getName())
                .phoneNumber(customerDTO.getPhoneNumber())
                .billingAddress(customerDTO.getBillingAddress())
                .postalCode(customerDTO.getPostalCode())
                .deliveryAddress(customerDTO.getDeliveryAddress())
                .gstNumber(customerDTO.getGstNumber())
                .dob(customerDTO.getDob())
                .whatsappAlerts(customerDTO.getWhatsappAlerts())
                .isCustomer(customerDTO.getIsCustomer())
                .build();
        
        customerRepo.save(customer);
        return customer.toCustomerDTO();
    }
}
