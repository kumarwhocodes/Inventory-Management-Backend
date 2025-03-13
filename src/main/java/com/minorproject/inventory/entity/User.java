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
    private String yourName;
    private String companyName;
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    private String gstNumber;
    private String licenseNumber;
    private Integer postalCode;
    private String upiId;
    
    public UserDTO toUserDTO() {
        return UserDTO.builder()
                .uid(uid != null ? uid.toString() : null)
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
