package com.ciaotada.hellokafka.service;

import com.ciaotada.hellokafka.model.Greetings;
import com.ciaotada.hellokafka.stream.GreetingsStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.EmbeddedHeaderUtils;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.stream.Collectors;

@Component
@Slf4j
public class GreetingsListener {
    @StreamListener(GreetingsStreams.INPUT)
//    @StreamListener(target = GreetingsStreams.INPUT, condition = "headers['x-test-header']=='OK'")
//    @StreamListener(target = GreetingsStreams.INPUT)
//    public void handleGreetings(@Payload Greetings greetings)
//    public void handleGreetings(Message<Greetings> greetings)
    public void handleGreetings(Message<byte[]> greetings)
//    public void handleGreetings(@Header("x-test-header") Message greetings)
    {
//        log.info("Received greetings payload: {}", greetings.getPayload().toString());
//        log.info("Received greetings headers: {}", greetings
//                .getHeaders().entrySet()
//                .stream()
//                .map(entry -> entry.getKey() + " - " + entry.getValue())
//                .collect(Collectors.joining(", ")));
        log.info("Received message: " +     greetings);


        byte[] payload = greetings.getPayload();
        try {
            MessageValues messageValues = EmbeddedHeaderUtils.extractHeaders(greetings, true);
            log.info(messageValues.getHeaders().entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.joining(", ")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
