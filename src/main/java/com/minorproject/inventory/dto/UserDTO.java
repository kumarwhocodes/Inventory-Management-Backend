package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.User;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String uid;
    private String yourName;
    private String companyName;
    private String phoneNumber;
    private String email;
    private String gstNumber;
    private String licenseNumber;
    private Integer postalCode;
    private String upiId;
    
    public User toUser() {
        return User.builder()
                .uid(uid != null ? UUID.fromString(uid) : null)
                .yourName(yourName)
                .companyName(companyName)
                .phoneNumber(phoneNumber)
                .email(email)
                .gstNumber(gstNumber)
                .licenseNumber(licenseNumber)
                .postalCode(postalCode)
                .upiId(upiId)
                .build();
    }
}
