package com.svalero.ecorepair.dto;

import com.svalero.ecorepair.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {

    @NotNull(message = "Role is required")
    private UserRole role;
}
