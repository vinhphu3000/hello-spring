package com.ciaotada.hellokafka.service;

import com.ciaotada.hellokafka.model.Greetings;
import com.ciaotada.hellokafka.stream.GreetingsStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

@Component
@Slf4j
public class GreetingsListener {
    @StreamListener(GreetingsStreams.INPUT)
    public void handleGreetings(@Payload Greetings greetings)
    {
        log.info("Received greetings: {}", greetings);
    }
}
