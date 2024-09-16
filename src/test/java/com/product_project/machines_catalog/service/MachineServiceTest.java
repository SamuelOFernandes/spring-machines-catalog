package com.product_project.machines_catalog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.product_project.machines_catalog.dto.MachineDTO;
import com.product_project.machines_catalog.exception.MachineNotFoundException;
import com.product_project.machines_catalog.models.Machine;
import com.product_project.machines_catalog.repository.MachineRepository;
import com.product_project.machines_catalog.messaging.MachineEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class MachineServiceTest {

    @Mock
    private MachineRepository machineRepository;

    @Mock
    private MachineEventPublisher eventPublisher;

    @InjectMocks
    private MachineService machineService;

    private Machine machine;
    private MachineDTO machineDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        machine = new Machine();
        machine.setId(1L);
        machine.setModel("Orange X200");
        machine.setCaptureRate(2.7);
        machine.setMaxPaymentValue(new BigDecimal("10000"));
        machine.setPrice(new BigDecimal("499.99"));
        machine.setActive(true);

        // Inicializando o MachineDTO
        machineDTO = new MachineDTO();
        machineDTO.setModel("Orange X200");
        machineDTO.setCaptureRate(2.7);
        machineDTO.setMaxPaymentValue(new BigDecimal("10000"));
        machineDTO.setPrice(new BigDecimal("499.99"));
        machineDTO.setActive(true);
    }


    @Test
    public void testFindById_NotFound() {

        doReturn(Optional.empty()).when(machineRepository).findById(1L);
        assertThrows(MachineNotFoundException.class, () -> {
            machineService.findById(1L);
        });
    }

    @Test
    public void testCreateMachine_Success() {

        doReturn(machine).when(machineRepository).save(any(Machine.class));

        Machine createdMachine = machineService.createMachine(machineDTO);
        assertNotNull(createdMachine);
        assertEquals("Orange X200", createdMachine.getModel());
        verify(eventPublisher, times(1)).publishMachineCreatedEvent(createdMachine);
    }

}
