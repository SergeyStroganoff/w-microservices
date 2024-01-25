package com.stroganov.mq;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.*;

@EnableBinding(ChannelBinding.class)
public interface ChannelBinding {

    String OUTPUT_CHANNEL = "loggingOutputChannel";

    @Output(OUTPUT_CHANNEL)
    @EnableListening
    MessageChannel loggingOutputChannel();
}
