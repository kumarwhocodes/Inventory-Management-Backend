package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.dto.PartyDTO;
import com.minorproject.inventory.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {
    
    private final PartyService service;
    
    @GetMapping
    public CustomResponse<List<PartyDTO>> fetchAllPartyHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Parties fetched successfully",
                service.fetchAllParty(token)
        );
    }
    
    @PostMapping
    public CustomResponse<PartyDTO> createPartyHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody PartyDTO partyDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.CREATED,
                "Party created successfully",
                service.createParty(token, partyDTO)
        );
    }
    
    @DeleteMapping("/{partyId}")
    public CustomResponse<Void> deletePartyHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String partyId
    ) {
        service.deleteParty(token, partyId);
        return new CustomResponse<>(
                HttpStatus.OK,
                "Party deleted successfully",
                null
        );
    }
    
    @GetMapping("/{partyId}")
    public CustomResponse<PartyDTO> fetchPartyByIdHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String partyId
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Party fetched successfully",
                service.fetchPartyById(token, partyId)
        );
    }
    
    @PutMapping("/{partyId}")
    public CustomResponse<PartyDTO> updatePartyHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String partyId,
            @RequestBody PartyDTO partyDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Party updated successfully",
                service.updateParty(token, partyId, partyDTO)
        );
    }
}
