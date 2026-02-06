package com.svalero.ecorepair.controller;

import com.svalero.ecorepair.dto.RepairInDto;
import com.svalero.ecorepair.dto.RepairOutDto;
import com.svalero.ecorepair.service.RepairService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    // GET /repairs
    @GetMapping
    public ResponseEntity<List<RepairOutDto>> getAllRepairs() {
        return ResponseEntity.ok(repairService.findAll());
    }

    // GET /repairs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RepairOutDto> getRepairById(@PathVariable long id) {
        RepairOutDto repair = repairService.findById(id);
        return ResponseEntity.ok(repair);
    }

    // POST /repairs
    @PostMapping
    public ResponseEntity<RepairOutDto> addRepair(
            @Valid @RequestBody RepairInDto repairInDto) {

        RepairOutDto repairOutDto = repairService.add(repairInDto);
        return new ResponseEntity<>(repairOutDto, HttpStatus.CREATED);
    }

    // PUT /repairs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RepairOutDto> updateRepair(
            @PathVariable long id,
            @Valid @RequestBody RepairInDto repairInDto) {

        RepairOutDto updatedRepair = repairService.modify(id, repairInDto);
        return ResponseEntity.ok(updatedRepair);
    }

    // DELETE /repairs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable long id) {
        repairService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
