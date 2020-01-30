package com.ciaotada.hellokafka.service;

import com.ciaotada.hellokafka.model.Greetings;
import com.ciaotada.hellokafka.stream.GreetingsStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.EmbeddedHeaderUtils;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GreetingsListener {
    @StreamListener(GreetingsStreams.INPUT)
    // TESTED ABLE TO SEE THE MESSAGE AND THE HEADERS
    // AND SHOULD BE USED WITH sendByteArray(String topic, final Greetings greetings) IN THE SENDER
    // THIS IS USED WITH contentType: text/plain but not JSON, AVRO
//    public void handleGreetings(Message<Greetings> greetings)
//    {
//        log.info("Received message payload: " + greetings.getPayload());
//        log.info("Received greetings headers: {}", greetings
//                .getHeaders().entrySet()
//                .stream()
//                .map(entry -> entry.getKey() + " - " + entry.getValue())
//                .collect(Collectors.joining(", ")));
//    }

    public void handleGreetings(@Payload String greetings, @Headers Map<String, Object> headers)
    {
        log.info("Received greetings: {}.", greetings);
//        headers.get(KafkaHeaders.RECEIVED_PARTITION_ID), headers.get(KafkaHeaders.OFFSET));
        log.info("Received greetings headers: {}", headers
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.joining(", ")));
    }



    // TESTED ABLE TO SEE THE MESSAGE
//    public void handleGreetings(@Payload Greetings greetings)
//    {
//        log.info("Received message: " + greetings);
//    }


    // TESTED ABLE TO SEE THE MESSAGE AND THE HEADERS
    // AND SHOULD BE USED WITH sendByteArray(String topic, final Greetings greetings) IN THE SENDER
//    public void handleGreetings(Message<byte[]> greetings)
//    {
//        byte[] payload = greetings.getPayload();
//
//        log.info("Received greetings payload: {}", greetings.getPayload().toString());
//
//        log.info("Received greetings headers: {}", greetings
//                .getHeaders().entrySet()
//                .stream()
//                .map(entry -> entry.getKey() + " - " + entry.getValue())
//                .collect(Collectors.joining(", ")));
//    }

    // TESTED NOT ABLE TO SEE THE MESSAGE AND THE HEADERS
    // AND SHOULD BE USED WITH sendByteArray(String topic, final Greetings greetings) IN THE SENDER
    // THIS IS ABLE TO USE WITH JSON AND AVRO
//    public void handleGreetings(Message<byte[]> greetings)
//    {
//        log.info(greetings.getPayload().toString());
//
//        try {
//            MessageValues messageValues = EmbeddedHeaderUtils.extractHeaders(greetings, true);
//            log.info(messageValues.getHeaders().entrySet()
//                    .stream()
//                    .map(entry -> entry.getKey() + " - " + entry.getValue())
//                    .collect(Collectors.joining(", ")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
