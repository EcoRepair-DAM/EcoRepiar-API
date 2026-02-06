package com.svalero.ecorepair.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repair")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double cost;

    @Column(name = "repair_date", nullable = false)
    private LocalDate repairDate;

    @Column(nullable = false)
    private boolean repair;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}
