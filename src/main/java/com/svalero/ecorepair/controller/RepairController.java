package com.reparaciones.api.controller;

import com.reparaciones.api.domain.Repair;
import com.reparaciones.api.dto.RepairInDto;
import com.reparaciones.api.dto.RepairOutDto;
import com.reparaciones.api.exception.ErrorResponse;
import com.reparaciones.api.exception.RepairNotFoundException;
import com.reparaciones.api.service.RepairService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @Autowired
    private ModelMapper modelMapper;

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
    public ResponseEntity<RepairOutDto> addRepair(@Valid @RequestBody RepairInDto repairInDto) {
        Repair repair = modelMapper.map(repairInDto, Repair.class);
        Repair newRepair = repairService.add(repair);
        RepairOutDto repairOutDto = modelMapper.map(newRepair, RepairOutDto.class);
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

    // ---------- EXCEPTION HANDLERS ----------

    @ExceptionHandler(RepairNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRepairNotFound(RepairNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.notFound(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorResponse errorResponse = ErrorResponse.validationError(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
        ErrorResponse errorResponse =
                ErrorResponse.generalError(500, "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
