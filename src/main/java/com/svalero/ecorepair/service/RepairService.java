package com.svalero.ecorepair.service;

import com.svalero.ecorepair.domain.Device;
import com.svalero.ecorepair.domain.Repair;
import com.svalero.ecorepair.dto.RepairInDto;
import com.svalero.ecorepair.dto.RepairOutDto;
import com.svalero.ecorepair.exception.DeviceNotFoundException;
import com.svalero.ecorepair.exception.RepairNotFoundException;
import com.svalero.ecorepair.repository.DeviceRepository;
import com.svalero.ecorepair.repository.RepairRepository;
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
    public RepairOutDto add(RepairInDto repairInDto) {

        Device device = deviceRepository.findById(repairInDto.getDeviceId())
                .orElseThrow(() ->
                        new DeviceNotFoundException(
                                "Device no encontrado con ID: " + repairInDto.getDeviceId()
                        ));

        Repair repair = new Repair();
        repair.setDescription(repairInDto.getDescription());
        repair.setCost(repairInDto.getCost());
        repair.setRepairDate(repairInDto.getRepairDate());
        repair.setRepair(repairInDto.getRepair());
        repair.setDevice(device);

        Repair repairGuardada = repairRepository.save(repair);
        return modelMapper.map(repairGuardada, RepairOutDto.class);
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

        Device device = deviceRepository.findById(repairInDto.getDeviceId())
                .orElseThrow(() ->
                        new DeviceNotFoundException(
                                "Device no encontrado con ID: " + repairInDto.getDeviceId()
                        ));

        repairExistente.setDescription(repairInDto.getDescription());
        repairExistente.setCost(repairInDto.getCost());
        repairExistente.setRepairDate(repairInDto.getRepairDate());
        repairExistente.setRepair(repairInDto.getRepair());
        repairExistente.setDevice(device);

        Repair repairGuardada = repairRepository.save(repairExistente);
        return modelMapper.map(repairGuardada, RepairOutDto.class);
    }
}
