package com.product_project.machines_catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_project.machines_catalog.models.Machine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.MessagePostProcessor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MachineEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private MessageProcessor messageProcessor;

    @InjectMocks
    private MachineEventPublisher machineEventPublisher;

    private Machine machine;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        machine = new Machine();
        machine.setId(1L);
        machine.setModel("Orange X200");
    }

    @Test
    public void testPublishMachineCreatedEvent_Success() throws JsonProcessingException {

        when(objectMapper.writeValueAsString(any(Machine.class))).thenReturn("{\"model\":\"Orange X200\"}");

        MessagePostProcessor messagePostProcessor = mock(MessagePostProcessor.class);
        when(messageProcessor.processMessage()).thenReturn(messagePostProcessor);

        machineEventPublisher.publishMachineCreatedEvent(machine);

        verify(rabbitTemplate, times(1)).convertAndSend(eq("machines.exchange"), eq(""), eq("{\"model\":\"Orange X200\"}"), eq(messagePostProcessor));
    }

    @Test
    public void testPublishMachineCreatedEvent_Failure() throws JsonProcessingException {

        when(objectMapper.writeValueAsString(any(Machine.class))).thenThrow(new JsonProcessingException("Serialization Error") {
        });

        assertThrows(RuntimeException.class, () -> {
            machineEventPublisher.publishMachineCreatedEvent(machine);
        });
    }
}
