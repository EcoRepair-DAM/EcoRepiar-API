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

    @NotBlank(message = "The name is mandatory")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 50)
    private String type;

    @NotBlank(message = "branch is mandatory")
    @Size(max = 50)
    private String brand;

    @NotNull(message = "You have to indicate if device is reusable")
    private Boolean reusable;

    @NotNull(message = "Purchase date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "purchase date cant be future")
    private LocalDate purchaseDate;

    // SETTERS MANUALES (para que Spring pueda mapear el JSON)

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
