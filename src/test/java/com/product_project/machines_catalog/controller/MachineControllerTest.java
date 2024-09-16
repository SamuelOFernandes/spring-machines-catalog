package com.product_project.machines_catalog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.product_project.machines_catalog.dto.MachineDTO;
import com.product_project.machines_catalog.models.Machine;
import com.product_project.machines_catalog.service.MachineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class MachineControllerTest {


    @Mock
    private MachineService machineService;

    @InjectMocks
    private MachineController machineController;


    @Mock
    private BindingResult bindingResult;


    private Machine machine;
    private MachineDTO machineDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        machine = new Machine();
        machine.setId(1L);
        machine.setModel("Orange X200");
        machine.setCaptureRate(2.7);
        machine.setMaxPaymentValue(new BigDecimal("10000"));
        machine.setPrice(new BigDecimal("499.99"));
        machine.setActive(true);

        machineDTO = new MachineDTO();
        machineDTO.setModel("Orange X200");
        machineDTO.setCaptureRate(2.7);
        machineDTO.setMaxPaymentValue(new BigDecimal("10000"));
        machineDTO.setPrice(new BigDecimal("499.99"));
        machineDTO.setActive(true);
    }

    @Test
    public void testGetMachines() {
        when(machineService.findMachines(null, null)).thenReturn(Arrays.asList(machine));

        ResponseEntity<List<Machine>> response = machineController.getMachines(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetMachineById_Success() {
        when(machineService.findById(1L)).thenReturn(machine);

        ResponseEntity<Machine> response = machineController.getMachineById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateMachine_Success() {

        when(bindingResult.hasErrors()).thenReturn(false);
        when(machineService.createMachine(any(MachineDTO.class))).thenReturn(machine);

        ResponseEntity<?> response = machineController.createMachine(machineDTO, bindingResult);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateMachine_Success() {

        when(bindingResult.hasErrors()).thenReturn(false);
        when(machineService.updateMachine(eq(1L), any(MachineDTO.class))).thenReturn(machine);

        ResponseEntity<?> response = machineController.updateMachine(1L, machineDTO, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    public void testDeleteMachine_Success() {
        doNothing().when(machineService).deleteMachine(1L);

        ResponseEntity<Void> response = machineController.deleteMachine(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testCreateMachine_ValidationErrors() {

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = machineController.createMachine(machineDTO, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateMachine_ValidationErrors() {

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = machineController.updateMachine(1L, machineDTO, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}