package com.stroganov.loggingservice.mq;

import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.function.Consumer;

@Configuration
@Getter
public class MessageBrokerConfig {

   private final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageBrokerConfig.class);

    private final Sinks.Many<Message<String>> innerBusSink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);

   // @Bean
   // public Consumer<Message<String>> onGettingMessageAction() {
//
   //     return message ->
   //             logger.info("Message received and logged: " + message.getPayload());
   //             System.out.println("Message received and logged: " + message.getPayload());
   // }
}


