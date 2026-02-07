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
public class DeviceOutDto {

    private long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Type is required")
    @Size(max = 50)
    private String type;

    @NotBlank(message = "Brand is required")
    @Size(max = 50)
    private String brand;

    @NotNull(message = "You must specify whether the device is reusable")
    private Boolean reusable;

    @NotNull(message = "Purchase date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
}
