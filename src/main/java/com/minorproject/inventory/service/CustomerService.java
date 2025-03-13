package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.PartyDTO;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.repository.CustomerRepository;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepo;
    
    public PartyDTO createCustomer(PartyDTO partyDTO) {
        Party customer = Party.builder()
                .name(partyDTO.getName())
                .phoneNumber(partyDTO.getPhoneNumber())
                .billingAddress(partyDTO.getBillingAddress())
                .postalCode(partyDTO.getPostalCode())
                .deliveryAddress(partyDTO.getDeliveryAddress())
                .gstNumber(partyDTO.getGstNumber())
                .dob(partyDTO.getDob())
                .whatsappAlerts(partyDTO.getWhatsappAlerts())
                .isCustomer(partyDTO.getIsCustomer())
                .build();
        
        customerRepo.save(customer);
        return customer.toCustomerDTO();
    }
}
