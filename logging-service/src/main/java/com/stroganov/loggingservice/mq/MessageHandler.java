package com.stroganov.loggingservice.mq;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

   private final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageHandler.class);

   @KafkaListener(topics = "warehouse")
   public void handleMessage(String message) {
      logger.info("Received message: {}", message);
      System.out.println("Received message: " + message);
   }
}


