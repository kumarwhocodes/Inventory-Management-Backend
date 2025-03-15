package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.PartyDTO;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.exception.TokenNotFound;
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
        
        // Update the party with new values
        existingParty.setName(partyDTO.getName());
        existingParty.setPhone(partyDTO.getPhone());
        existingParty.setBillingAddress(partyDTO.getBillingAddress());
        existingParty.setPostalCode(partyDTO.getPostalCode());
        existingParty.setDeliveryAddress(partyDTO.getDeliveryAddress());
        existingParty.setGstNumber(partyDTO.getGstNumber());
        existingParty.setDob(partyDTO.getDob());
        existingParty.setWhatsappAlerts(partyDTO.getWhatsappAlerts());
        existingParty.setIsCustomer(partyDTO.getIsCustomer());
        
        Party updatedParty = partyRepo.save(existingParty);
        
        return updatedParty.toPartyDTO();
    }
}