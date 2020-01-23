package com.ciaotada.hellokafka.service;

import com.ciaotada.hellokafka.model.Greetings;
import com.ciaotada.hellokafka.stream.GreetingsStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.EmbeddedHeaderUtils;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@Slf4j
public class GreetingsService {
    private final GreetingsStreams greetingsStreams;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public GreetingsService(GreetingsStreams greetingsStreams, KafkaTemplate<String, byte[]> kafkaTemplate)
    {
        this.greetingsStreams = greetingsStreams;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGreeting(final Greetings greetings)
    {
        log.info("Sending greetings {}", greetings);


//        Map<String, String> headers = new HashMap<>();
//        headers.put("x-test-header", "OK");
//        headers.put("" + MessageHeaders.CONTENT_TYPE, ""+ MimeTypeUtils.APPLICATION_JSON);

        Message<byte[]> message = MessageBuilder
            .withPayload(greetings.toString().getBytes())
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
            .setHeader("Custom-header", "Custom-value")
            .build();

        MessageValues messageValues = new MessageValues(message);

        byte[] fullPayload = EmbeddedHeaderUtils.embedHeaders(messageValues, "Custom-header");

        kafkaTemplate.send("greetings2", fullPayload);

//        MessageChannel messageChannel = greetingsStreams.outboundGreetings();
//        messageChannel.send(MessageBuilder
//                .withPayload(greetings)
//                .setHeader("CustomHeader","CustomValue")
//                .build());
    }
}
