package com.test.prueba.model.dto;

public record LoginResponse(
        String token,
        String tokenType
) {
}
