package com.minorproject.inventory.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.minorproject.inventory.dto.AccessTokenBody;
import com.minorproject.inventory.dto.UserDTO;
import com.minorproject.inventory.entity.BankDetails;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.FirebaseOperationException;
import com.minorproject.inventory.exception.InvalidToken;
import com.minorproject.inventory.repository.BankDetailsRepository;
import com.minorproject.inventory.repository.UserRepository;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repo;
    private final BankDetailsRepository bankRepo;
    private final FirebaseAuthUtil firebaseAuthUtil;
    
    public UserDTO authUser(AccessTokenBody tokenBody) {
        FirebaseToken decodedToken = firebaseAuthUtil.validateTokenWithoutPrefix(tokenBody.getToken());
        
        try {
            UserRecord firebaseUser = FirebaseAuth.getInstance().getUser(decodedToken.getUid());
            
            // Check if user exists by email
            Optional<User> existingUser = repo.findByEmail(firebaseUser.getEmail());
            if (existingUser.isPresent()) {
                return existingUser.get().toUserDTO();
            } else {
                return createUser(firebaseUser);
            }
        } catch (FirebaseAuthException e) {
            log.error("Error fetching Firebase user: {}", e.getMessage());
            throw new InvalidToken();
        }
    }
    
    public UserDTO fetchUser(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        return user.toUserDTO();
    }
    
    public UserDTO updateUser(String token, UserDTO userDTO) {
        User existingUser = firebaseAuthUtil.getUserFromToken(token);
        
        existingUser.setContactName(userDTO.getContactName());
        existingUser.setBusinessName(userDTO.getBusinessName());
        existingUser.setContactNumber(userDTO.getContactNumber());
        existingUser.setGstNumber(userDTO.getGstNumber());
        existingUser.setLicenseNumber(userDTO.getLicenseNumber());
        existingUser.setPostalCode(userDTO.getPostalCode());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setIndustry(userDTO.getIndustry());
        // Don't update email as it's tied to authentication
        
        User updatedUser = repo.save(existingUser);
        return updatedUser.toUserDTO();
    }
    
    public void deleteUser(String token) {
        FirebaseToken decodedToken = firebaseAuthUtil.validateAndDecodeToken(token);
        String firebaseUid = decodedToken.getUid();
        
        User existingUser = firebaseAuthUtil.getUserFromToken(token);
        
        try {
            FirebaseAuth.getInstance().deleteUser(firebaseUid);
            repo.delete(existingUser);
        } catch (FirebaseAuthException e) {
            throw new FirebaseOperationException("Failed to delete user from Firebase: " + e.getMessage());
        }
    }
    
    private UserDTO createUser(UserRecord firebaseUser) {
        User user = User
                .builder()
                .contactName(firebaseUser.getDisplayName())
                .businessName(firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "Default Company")
                .contactNumber(firebaseUser.getPhoneNumber())
                .email(firebaseUser.getEmail())
                .build();
        repo.save(user);
        
        BankDetails bankDetails = BankDetails.builder()
                .accountNumber("")
                .upiId("")
                .bankName("")
                .holderName("")
                .ifscCode("")
                .user(user)
                .build();
        bankRepo.save(bankDetails);
        
        return user.toUserDTO();
    }
}