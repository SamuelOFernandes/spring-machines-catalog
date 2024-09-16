package com.product_project.machines_catalog.messaging;

import org.springframework.amqp.core.MessagePostProcessor;

public interface MessageProcessor {

    MessagePostProcessor processMessage();

}
