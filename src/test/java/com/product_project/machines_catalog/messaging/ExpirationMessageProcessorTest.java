package com.product_project.machines_catalog.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExpirationMessageProcessorTest {

    private ExpirationMessageProcessor expirationMessageProcessor;

    @BeforeEach
    public void setUp() {
        expirationMessageProcessor = new ExpirationMessageProcessor();
    }

    @Test
    public void testProcessMessage_SetsExpiration() {
        Message message = mock(Message.class);
        MessageProperties messageProperties = mock(MessageProperties.class);

        when(message.getMessageProperties()).thenReturn(messageProperties);

        MessagePostProcessor processor = expirationMessageProcessor.processMessage();
        Message processedMessage = processor.postProcessMessage(message);

        verify(messageProperties, times(1)).setExpiration("60000");
        assertEquals(message, processedMessage);
    }
}