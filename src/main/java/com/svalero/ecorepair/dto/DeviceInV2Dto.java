package com.svalero.ecorepair.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class DeviceInV2Dto {

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
    @PastOrPresent(message = "Purchase date cannot be future")
    private LocalDate purchaseDate;

    private String imageUrl;

    private MultipartFile file;
}
