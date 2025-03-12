package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.Customer;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String id;
    private String name;
    private String phoneNumber;
    private String billingAddress;
    private Integer postalCode;
    private String deliveryAddress;
    private String gstNumber;
    private LocalDate dob;
    private Boolean whatsappAlerts;
    private Boolean isCustomer;
    
    public Customer toCustomer() {
        return Customer.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .name(name)
                .phoneNumber(phoneNumber)
                .billingAddress(billingAddress)
                .postalCode(postalCode)
                .deliveryAddress(deliveryAddress)
                .gstNumber(gstNumber)
                .dob(dob)
                .whatsappAlerts(whatsappAlerts)
                .isCustomer(isCustomer)
                .build();
    }
}
