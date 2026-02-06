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
public class RepairOutDto {

    private long id;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200)
    private String description;

    @NotNull(message = "El coste es obligatorio")
    @Min(value = 0, message = "El coste debe ser positivo")
    private Double cost;

    @NotNull(message = "La fecha de reparación es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate repairDate;

    @NotNull(message = "Debes indicar si la reparación está finalizada")
    private Boolean repair;

    @NotNull(message = "El deviceId es obligatorio")
    private Long deviceId;
}
