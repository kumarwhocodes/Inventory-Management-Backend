package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String uid;
    private String photo;
    private String industry;
    private String contactName;
    private String businessName;
    private String contactNumber;
    private String email;
    private String gstNumber;
    private String licenseNumber;
    private String address;
    private Integer postalCode;
    private String signature;
    
    public User toUser() {
        return User.builder()
                .uid(uid != null ? UUID.fromString(uid) : null)
                .photo(photo)
                .industry(industry)
                .contactName(contactName)
                .businessName(businessName)
                .contactNumber(contactNumber)
                .email(email)
                .gstNumber(gstNumber)
                .licenseNumber(licenseNumber)
                .postalCode(postalCode)
                .address(address)
                .signature(signature)
                .build();
    }
}
