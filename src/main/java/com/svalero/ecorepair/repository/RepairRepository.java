package com.reparaciones.api.repository;

import com.reparaciones.api.domain.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
}