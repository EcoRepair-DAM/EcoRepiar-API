package com.reparaciones.api.service;

import com.reparaciones.api.domain.Device;
import com.reparaciones.api.domain.Repair;
import com.reparaciones.api.dto.RepairInDto;
import com.reparaciones.api.dto.RepairOutDto;
import com.reparaciones.api.exception.DeviceNotFoundException;
import com.reparaciones.api.exception.RepairNotFoundException;
import com.reparaciones.api.repository.DeviceRepository;
import com.reparaciones.api.repository.RepairRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairService {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    // POST /repairs
    public Repair add(Repair repair) {
        return repairRepository.save(repair);
    }

    // DELETE /repairs/{id}
    public void delete(long id) {
        Repair repair = repairRepository.findById(id)
                .orElseThrow(() ->
                        new RepairNotFoundException("Repair no encontrada con ID: " + id));

        repairRepository.delete(repair);
    }

    // GET /repairs
    public List<RepairOutDto> findAll() {
        List<Repair> repairs = repairRepository.findAll();
        return modelMapper.map(repairs, new TypeToken<List<RepairOutDto>>() {}.getType());
    }

    // GET /repairs/{id}
    public RepairOutDto findById(long id) {
        Repair repair = repairRepository.findById(id)
                .orElseThrow(() ->
                        new RepairNotFoundException("Repair no encontrada con ID: " + id));

        return modelMapper.map(repair, RepairOutDto.class);
    }

    // PUT /repairs/{id}
    public RepairOutDto modify(long id, RepairInDto repairInDto) {
        Repair repairExistente = repairRepository.findById(id)
                .orElseThrow(() ->
                        new RepairNotFoundException("Repair no encontrada con ID: " + id));

        // Volcamos los datos simples del DTO
        modelMapper.map(repairInDto, repairExistente);

        // Gestionamos la relación con Device (FK)
        Device device = deviceRepository.findById(repairInDto.getDeviceId())
                .orElseThrow(() ->
                        new DeviceNotFoundException("Device no encontrado con ID: " + repairInDto.getDeviceId()));

        repairExistente.setDevice(device);

        // Aseguramos el ID
        repairExistente.setId(id);

        Repair repairGuardada = repairRepository.save(repairExistente);
        return modelMapper.map(repairGuardada, RepairOutDto.class);
    }
}
