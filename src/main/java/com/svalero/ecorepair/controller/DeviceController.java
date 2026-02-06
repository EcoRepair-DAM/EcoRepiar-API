package com.svalero.ecorepair.controller;

import com.svalero.ecorepair.dto.DeviceInDto;
import com.svalero.ecorepair.dto.DeviceOutDto;
import com.svalero.ecorepair.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

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
    public ResponseEntity<DeviceOutDto> addDevice(
            @Valid @RequestBody DeviceInDto deviceInDto) {

        DeviceOutDto deviceOutDto = deviceService.add(deviceInDto);
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
}
