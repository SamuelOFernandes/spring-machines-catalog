package com.product_project.machines_catalog.messaging;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExpirationMessageProcessor implements MessageProcessor {

    @Override
    public MessagePostProcessor processMessage() {
        return message -> {
            message.getMessageProperties().setExpiration("60000");
            return message;
        };
    }

}
