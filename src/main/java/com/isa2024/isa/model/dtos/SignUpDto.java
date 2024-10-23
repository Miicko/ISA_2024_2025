package com.isa2024.isa.model.dtos;

import com.isa2024.isa.model.enums.UserRole;

public record SignUpDto(
        String username,
        String password,
        UserRole role,
        String email,
        String firstName,
        String lastName,
        String address
        ) {
}
