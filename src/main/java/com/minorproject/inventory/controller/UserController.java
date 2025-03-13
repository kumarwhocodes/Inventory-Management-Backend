package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.AccessTokenBody;
import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.dto.UserDTO;
import com.minorproject.inventory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService service;
    
    @PostMapping
    public CustomResponse<UserDTO> authUserHandler(@RequestBody AccessTokenBody tokenBody) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "User Logged Successfully",
                service.authUser(tokenBody)
        );
    }
    
    @GetMapping
    public CustomResponse<UserDTO> fetchUserHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "User Fetched Successfully.",
                service.fetchUser(token)
        );
    }
    
    @PutMapping
    public CustomResponse<UserDTO> updateUserHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody UserDTO userDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "User Updated Successfully",
                service.updateUser(token, userDTO)
        );
    }
    
    @DeleteMapping
    public CustomResponse<Void> deleteUserHandler(
            @RequestHeader("Authorization") String token
    ) {
        service.deleteUser(token);
        return new CustomResponse<>(
                HttpStatus.OK,
                "User deleted successfully",
                null
        );
    }
    
}
