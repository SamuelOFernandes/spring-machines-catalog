package com.product_project.machines_catalog.service;

import com.product_project.machines_catalog.dto.MachineDTO;
import com.product_project.machines_catalog.exception.MachineNotFoundException;
import com.product_project.machines_catalog.messaging.MachineEventPublisher;
import com.product_project.machines_catalog.models.Machine;
import com.product_project.machines_catalog.repository.MachineRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MachineService {

    private final MachineRepository repository;
    private final MachineEventPublisher eventPublisher;

    public MachineService(MachineRepository repository, MachineEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }


    public List<Machine> findMachines(Boolean hasPrinter, Boolean isActive) {
        if (hasPrinter != null && isActive != null) {
            return repository.findByHasPrinterAndIsActive(hasPrinter, isActive);
        } else if (hasPrinter != null) {
            return repository.findByHasPrinter(hasPrinter);
        } else if (isActive != null) {
            return repository.findByIsActive(isActive);
        } else {
            return repository.findAll();
        }
    }

    public Machine findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new MachineNotFoundException("Machine not found with id: " + id));
    }

    public List<Machine> findMachinesByModel(String model) {
        List<Machine> machines = repository.findByModelContainingIgnoreCase(model);
        if (machines == null || machines.isEmpty()) {
            throw new MachineNotFoundException("No machines found with model: " + model);
        }
        return machines;
    }


    @Transactional
    public Machine createMachine( MachineDTO machineDTO) {
        Machine machine = mapToEntity(machineDTO);
        Machine savedMachine = repository.save(machine);
        eventPublisher.publishMachineCreatedEvent(savedMachine);
        return savedMachine;
    }

    @Transactional
    public Machine updateMachine(Long id, MachineDTO machineDTO) {
        Machine existingMachine = findById(id);
        updateEntity(existingMachine, machineDTO);
        return repository.save(existingMachine);
    }

    @Transactional
    public void deleteMachine(Long id) {
        Machine existingMachine = findById(id);
        repository.delete(existingMachine);
    }
    
    private Machine mapToEntity(MachineDTO machineDTO) {
        Machine machine = new Machine();
        machine.setModel(machineDTO.getModel());
        machine.setPaymentCategory(machineDTO.getPaymentCategory());
        machine.setCaptureRate(machineDTO.getCaptureRate());
        machine.setHasPrinter(machineDTO.isHasPrinter());
        machine.setMaxPaymentValue(machineDTO.getMaxPaymentValue());
        machine.setMaxInstallmentsWithoutInterest(machineDTO.getMaxInstallmentsWithoutInterest());
        machine.setPrice(machineDTO.getPrice());
        machine.setActive(machineDTO.isActive());
        return machine;
    }

    private void updateEntity(Machine existingMachine, MachineDTO machineDTO) {

        existingMachine.setModel(machineDTO.getModel());
        existingMachine.setPaymentCategory(machineDTO.getPaymentCategory());
        existingMachine.setCaptureRate(machineDTO.getCaptureRate());
        existingMachine.setHasPrinter(machineDTO.isHasPrinter());
        existingMachine.setMaxPaymentValue(machineDTO.getMaxPaymentValue());
        existingMachine.setMaxInstallmentsWithoutInterest(machineDTO.getMaxInstallmentsWithoutInterest());
        existingMachine.setPrice(machineDTO.getPrice());
        existingMachine.setActive(machineDTO.isActive());
    }
}