package com.svalero.ecorepair.controller;

import com.svalero.ecorepair.domain.Device;
import com.svalero.ecorepair.dto.DeviceInV2Dto;
import com.svalero.ecorepair.dto.DeviceOutV2Dto;
import com.svalero.ecorepair.service.DeviceService;
import com.svalero.ecorepair.util.ImagenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v2/devices")
public class DeviceV2Controller {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ImagenUtil imagenUtil;

    @GetMapping
    public ResponseEntity<List<DeviceOutV2Dto>> getAllDevicesV2() {
        return ResponseEntity.ok(deviceService.findAllV2());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceOutV2Dto> getDeviceByIdV2(@PathVariable long id) {
        return ResponseEntity.ok(deviceService.findByIdV2(id));
    }

    @PostMapping
    public ResponseEntity<DeviceOutV2Dto> addDeviceV2(
            @Valid @ModelAttribute DeviceInV2Dto deviceInV2Dto) {

        if (deviceInV2Dto.getFile() != null && !deviceInV2Dto.getFile().isEmpty()) {
            deviceInV2Dto.setImageUrl(imagenUtil.procesarImagen(deviceInV2Dto.getFile()));
        }

        return new ResponseEntity<>(
                deviceService.addV2(deviceInV2Dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceOutV2Dto> updateDeviceV2(
            @PathVariable long id,
            @Valid @ModelAttribute DeviceInV2Dto deviceInV2Dto) {

        Device currentDevice = deviceService.findEntityById(id);

        if (deviceInV2Dto.getFile() != null && !deviceInV2Dto.getFile().isEmpty()) {
            imagenUtil.eliminarImagen(currentDevice.getImageUrl());
            deviceInV2Dto.setImageUrl(imagenUtil.procesarImagen(deviceInV2Dto.getFile()));
        } else {
            deviceInV2Dto.setImageUrl(currentDevice.getImageUrl());
        }

        return ResponseEntity.ok(deviceService.modifyV2(id, deviceInV2Dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceV2(@PathVariable long id) {
        Device currentDevice = deviceService.findEntityById(id);
        imagenUtil.eliminarImagen(currentDevice.getImageUrl());
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
