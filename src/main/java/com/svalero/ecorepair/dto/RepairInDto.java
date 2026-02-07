package com.svalero.ecorepair.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairInDto {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Cost is required")
    @Min(value = 0, message = "Cost must be positive")
    private Double cost;

    @NotNull(message = "Repair date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate repairDate;

    @NotNull(message = "You must specify whether the repair is completed")
    private Boolean repair;

    @NotNull(message = "Device ID is required")
    private Long deviceId;

    // 🔽 SETTERS MANUALES (para que Spring pueda mapear el JSON)

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setRepairDate(LocalDate repairDate) {
        this.repairDate = repairDate;
    }

    public void setRepair(Boolean repair) {
        this.repair = repair;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
