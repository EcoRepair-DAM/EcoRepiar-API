package com.svalero.ecorepair.service;

import com.svalero.ecorepair.domain.Device;
import com.svalero.ecorepair.dto.DeviceInDto;
import com.svalero.ecorepair.dto.DeviceInV2Dto;
import com.svalero.ecorepair.dto.DeviceOutDto;
import com.svalero.ecorepair.dto.DeviceOutV2Dto;
import com.svalero.ecorepair.exception.DeviceNotFoundException;
import com.svalero.ecorepair.repository.DeviceRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    // POST /devices
    public DeviceOutDto add(DeviceInDto deviceInDto) {

        Device device = modelMapper.map(deviceInDto, Device.class);

        Device deviceGuardado = deviceRepository.save(device);
        return modelMapper.map(deviceGuardado, DeviceOutDto.class);
    }

    // DELETE /devices/{id}
    public void delete(long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + id));

        deviceRepository.delete(device);
    }

    // GET /devices
    public List<DeviceOutDto> findAll() {
        List<Device> devices = deviceRepository.findAll();
        return modelMapper.map(devices, new TypeToken<List<DeviceOutDto>>() {}.getType());
    }

    // GET /devices/{id}
    public DeviceOutDto findById(long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + id));

        return modelMapper.map(device, DeviceOutDto.class);
    }

    // PUT /devices/{id}
    public DeviceOutDto modify(long id, DeviceInDto deviceInDto) {

        Device deviceExistente = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + id));

        // Volcamos los datos simples
        modelMapper.map(deviceInDto, deviceExistente);

        Device deviceGuardado = deviceRepository.save(deviceExistente);
        return modelMapper.map(deviceGuardado, DeviceOutDto.class);
    }

    // --- V2 ---

    // GET /v2/devices
    public List<DeviceOutV2Dto> findAllV2() {
        List<Device> devices = deviceRepository.findAll();
        return modelMapper.map(devices, new TypeToken<List<DeviceOutV2Dto>>() {}.getType());
    }

    // GET /v2/devices/{id}
    public DeviceOutV2Dto findByIdV2(long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + id));
        return modelMapper.map(device, DeviceOutV2Dto.class);
    }

    // POST /v2/devices
    public DeviceOutV2Dto addV2(DeviceInV2Dto deviceInV2Dto) {
        Device device = new Device();
        device.setName(deviceInV2Dto.getName());
        device.setType(deviceInV2Dto.getType());
        device.setBrand(deviceInV2Dto.getBrand());
        device.setReusable(deviceInV2Dto.getReusable());
        device.setPurchaseDate(deviceInV2Dto.getPurchaseDate());
        device.setImageUrl(deviceInV2Dto.getImageUrl());

        Device saved = deviceRepository.save(device);
        return modelMapper.map(saved, DeviceOutV2Dto.class);
    }

    // PUT /v2/devices/{id}
    public DeviceOutV2Dto modifyV2(long id, DeviceInV2Dto deviceInV2Dto) {
        Device existing = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + id));

        existing.setName(deviceInV2Dto.getName());
        existing.setType(deviceInV2Dto.getType());
        existing.setBrand(deviceInV2Dto.getBrand());
        existing.setReusable(deviceInV2Dto.getReusable());
        existing.setPurchaseDate(deviceInV2Dto.getPurchaseDate());
        existing.setImageUrl(deviceInV2Dto.getImageUrl());

        Device saved = deviceRepository.save(existing);
        return modelMapper.map(saved, DeviceOutV2Dto.class);
    }

    // Devuelve la entidad directamente (usado en el controller V2 para gestionar imágenes)
    public Device findEntityById(long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + id));
    }
}
