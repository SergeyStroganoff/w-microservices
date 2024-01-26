package com.stroganov.mq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Getter
public class MessageHandler {

    private final MessageBrokerConfig messageBrokerConfig;

    @Autowired
    public MessageHandler(MessageBrokerConfig messageBrokerConfig) {
        this.messageBrokerConfig = messageBrokerConfig;
    }

    public void sendMessage(String message) {
        MessageHeaders headers = new MessageHeaders(null);
        messageBrokerConfig.getInnerBusSink().emitNext(MessageBuilder.createMessage(message,headers), Sinks.EmitFailureHandler.FAIL_FAST);
        System.out.println("Message sent: " + message);
    }

}
