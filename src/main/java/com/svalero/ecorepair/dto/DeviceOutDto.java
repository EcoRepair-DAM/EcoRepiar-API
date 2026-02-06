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

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50)
    private String type;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50)
    private String brand;

    @NotNull(message = "Debes indicar si el dispositivo es reutilizable")
    private Boolean reusable;

    @NotNull(message = "La fecha de compra es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
}
