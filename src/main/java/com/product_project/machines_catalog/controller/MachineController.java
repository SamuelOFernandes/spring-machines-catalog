package com.product_project.machines_catalog.controller;


import com.product_project.machines_catalog.dto.MachineDTO;
import com.product_project.machines_catalog.models.Machine;
import com.product_project.machines_catalog.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService service;

    public MachineController(MachineService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Machine>> getMachines(
            @RequestParam(required = false) Boolean hasPrinter,
            @RequestParam(required = false) Boolean isActive) {
        List<Machine> machines = service.findMachines(hasPrinter, isActive);
        return ResponseEntity.ok(machines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Machine> getMachineById(@PathVariable Long id) {
        Machine machine = service.findById(id);
        return ResponseEntity.ok(machine);
    }

    @PostMapping
    public ResponseEntity<?> createMachine(@RequestBody @Valid MachineDTO machineDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Machine newMachine = service.createMachine(machineDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMachine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMachine(@PathVariable Long id, @RequestBody @Valid MachineDTO machineDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Machine updatedMachine = service.updateMachine(id, machineDTO);
        return ResponseEntity.ok(updatedMachine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        service.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/model")
    public ResponseEntity<List<Machine>> searchMachinesByModel(@RequestParam String model) {
        List<Machine> machines = service.findMachinesByModel(model);
        return ResponseEntity.ok(machines);
    }

}