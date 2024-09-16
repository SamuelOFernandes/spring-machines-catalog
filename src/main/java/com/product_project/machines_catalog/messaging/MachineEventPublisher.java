package com.product_project.machines_catalog.messaging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_project.machines_catalog.models.Machine;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MachineEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final MessageProcessor messageProcessor;

    private static final String EXCHANGE_NAME = "machines.exchange";

    public MachineEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, MessageProcessor messageProcessor) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.messageProcessor = messageProcessor;
    }

    public void publishMachineCreatedEvent(Machine machine) {
        try {
            String machineJson = objectMapper.writeValueAsString(machine);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", machineJson, messageProcessor.processMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert machine object to JSON", e);
        }
    }
}