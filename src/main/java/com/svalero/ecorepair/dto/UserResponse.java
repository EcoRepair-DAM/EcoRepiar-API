package com.svalero.ecorepair.dto;

import com.svalero.ecorepair.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private UserRole role;
}
