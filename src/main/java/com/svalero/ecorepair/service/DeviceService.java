package com.reparaciones.api.service;

import com.reparaciones.api.domain.Device;
import com.reparaciones.api.dto.DeviceInDto;
import com.reparaciones.api.dto.DeviceOutDto;
import com.reparaciones.api.exception.DeviceNotFoundException;
import com.reparaciones.api.repository.DeviceRepository;
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
    public Device add(Device device) {
        return deviceRepository.save(device);
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

        // Volcamos el DTO sobre la entidad existente
        modelMapper.map(deviceInDto, deviceExistente);

        // Aseguramos el ID
        deviceExistente.setId(id);

        Device deviceGuardado = deviceRepository.save(deviceExistente);
        return modelMapper.map(deviceGuardado, DeviceOutDto.class);
    }
}
