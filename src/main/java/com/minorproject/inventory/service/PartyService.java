package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.PartyDTO;
import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.repository.CategoryRepository;
import com.minorproject.inventory.repository.PartyRepository;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyService {
    
    private final PartyRepository partyRepo;
    private final CategoryRepository categoryRepo;
    private final FirebaseAuthUtil firebaseAuthUtil;
    
    public List<PartyDTO> fetchAllParty(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Party> parties = partyRepo.findByUser(user);
        return parties.stream()
                .map(Party::toPartyDTO)
                .collect(Collectors.toList());
    }
    
    public PartyDTO createParty(String token, PartyDTO partyDTO) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Party party = partyDTO.toParty();
        
        if (partyDTO.getCategory() != null) {
            Category category = categoryRepo.findByCategoryName(partyDTO.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + partyDTO.getCategory()));
            party.setCategory(category);
        }
        
        party.setUser(user);
        
        Party savedParty = partyRepo.save(party);
        return savedParty.toPartyDTO();
    }
    
    public void deleteParty(String token, String partyId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Party party = partyRepo.findByIdAndUser(UUID.fromString(partyId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + partyId));
        
        partyRepo.delete(party);
    }
    
    public PartyDTO fetchPartyById(String token, String partyId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Party party = partyRepo.findByIdAndUser(UUID.fromString(partyId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + partyId));
        
        return party.toPartyDTO();
    }
    
    public PartyDTO updateParty(String token, String partyId, PartyDTO partyDTO) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Party existingParty = partyRepo.findByIdAndUser(UUID.fromString(partyId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + partyId));
        
        // Update only non-null fields
        if (partyDTO.getName() != null) existingParty.setName(partyDTO.getName());
        if (partyDTO.getPhone() != null) existingParty.setPhone(partyDTO.getPhone());
        if (partyDTO.getBillingAddress() != null) existingParty.setBillingAddress(partyDTO.getBillingAddress());
        if (partyDTO.getPostalCode() != null) existingParty.setPostalCode(partyDTO.getPostalCode());
        if (partyDTO.getDeliveryAddress() != null) existingParty.setDeliveryAddress(partyDTO.getDeliveryAddress());
        if (partyDTO.getGstNumber() != null) existingParty.setGstNumber(partyDTO.getGstNumber());
        if (partyDTO.getDob() != null) existingParty.setDob(partyDTO.getDob());
        if (partyDTO.getIsCustomer() != null) existingParty.setIsCustomer(partyDTO.getIsCustomer());
        
        if (partyDTO.getCategory() != null) {
            Category category = categoryRepo.findByCategoryName(partyDTO.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + partyDTO.getCategory()));
            existingParty.setCategory(category);
        }
        
        Party updatedParty = partyRepo.save(existingParty);
        return updatedParty.toPartyDTO();
    }
}