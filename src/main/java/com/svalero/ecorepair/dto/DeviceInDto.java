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
public class DeviceInDto {

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
    @PastOrPresent(message = "La fecha de compra no puede ser futura")
    private LocalDate purchaseDate;

    // 🔽 SETTERS MANUALES (para que Spring pueda mapear el JSON)

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setReusable(Boolean reusable) {
        this.reusable = reusable;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
