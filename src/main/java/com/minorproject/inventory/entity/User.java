package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    private String photo;
    private String industry;
    private String contactName;
    private String businessName;
    private String contactNumber;
    @Column(nullable = false, unique = true)
    private String email;
    private String gstNumber;
    private String licenseNumber;
    private String address;
    private Integer postalCode;
    private String signature;
    
    public UserDTO toUserDTO() {
        return UserDTO.builder()
                .uid(uid != null ? uid.toString() : null)
                .photo(photo)
                .industry(industry)
                .contactName(contactName)
                .businessName(businessName)
                .contactNumber(contactNumber)
                .email(email)
                .gstNumber(gstNumber)
                .licenseNumber(licenseNumber)
                .address(address)
                .postalCode(postalCode)
                .signature(signature)
                .build();
    }
}
