package com.stroganov.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerKafkaImpl implements MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    public MessageProducerKafkaImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String message) {
        kafkaTemplate.send(topic, message);
    }
}
