package com.minorproject.inventory.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}