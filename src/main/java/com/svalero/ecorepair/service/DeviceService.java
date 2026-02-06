package com.svalero.ecorepair.service;

import com.svalero.ecorepair.domain.Device;
import com.svalero.ecorepair.dto.DeviceInDto;
import com.svalero.ecorepair.dto.DeviceOutDto;
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
}
