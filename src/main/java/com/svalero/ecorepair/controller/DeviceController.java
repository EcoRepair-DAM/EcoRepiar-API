package com.reparaciones.api.controller;

import com.reparaciones.api.domain.Device;
import com.reparaciones.api.dto.DeviceInDto;
import com.reparaciones.api.dto.DeviceOutDto;
import com.reparaciones.api.exception.DeviceNotFoundException;
import com.reparaciones.api.exception.ErrorResponse;
import com.reparaciones.api.service.DeviceService;
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
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ModelMapper modelMapper;

    // GET /devices
    @GetMapping
    public ResponseEntity<List<DeviceOutDto>> getAllDevices() {
        return ResponseEntity.ok(deviceService.findAll());
    }

    // GET /devices/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DeviceOutDto> getDeviceById(@PathVariable long id) {
        DeviceOutDto device = deviceService.findById(id);
        return ResponseEntity.ok(device);
    }

    // POST /devices
    @PostMapping
    public ResponseEntity<DeviceOutDto> addDevice(@Valid @RequestBody DeviceInDto deviceInDto) {
        Device device = modelMapper.map(deviceInDto, Device.class);
        Device newDevice = deviceService.add(device);
        DeviceOutDto deviceOutDto = modelMapper.map(newDevice, DeviceOutDto.class);
        return new ResponseEntity<>(deviceOutDto, HttpStatus.CREATED);
    }

    // PUT /devices/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DeviceOutDto> updateDevice(
            @PathVariable long id,
            @Valid @RequestBody DeviceInDto deviceInDto) {

        DeviceOutDto updatedDevice = deviceService.modify(id, deviceInDto);
        return ResponseEntity.ok(updatedDevice);
    }

    // DELETE /devices/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable long id) {
        deviceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ---------- EXCEPTION HANDLERS ----------

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeviceNotFound(DeviceNotFoundException ex) {
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
