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

import java.util.HashMap;
import java.util.Map;

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

//    public GreetingsService(GreetingsStreams greetingsStreams)
//    {
//        this.greetingsStreams = greetingsStreams;
//    }

    public void sendGreeting(final Greetings greetings)
    {
//        log.info("Sending greetings {}", greetings);

        sendByteArray("greetings2", greetings);

//        sendByMessageChannel(greetings);
    }

    private void sendByMessageChannel(final Greetings greetings)
    {
        Map<String, Object> headers = new HashMap<String, Object>();

        headers.put("CustomHeader","CustomValue");

        MessageHeaders messageHeaders = new MessageHeaders(headers);

        MessageChannel messageChannel = greetingsStreams.outboundGreetings();
        messageChannel.send(MessageBuilder.createMessage(greetings, messageHeaders));
    }

    // TESTED, ABLE TO SEND BUT CANNOT SEE THE HEADERS IN THE LISTENER
//    private void sendByMessageChannel(final Greetings greetings)
//    {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("CustomHeader", "CustomValue");
//        headers.put("" + MessageHeaders.CONTENT_TYPE, ""+ MimeTypeUtils.APPLICATION_JSON);
//
//        MessageChannel messageChannel = greetingsStreams.outboundGreetings();
//        messageChannel.send(MessageBuilder
//                .withPayload(greetings)
//                .copyHeaders(headers)
//                .build());
//    }

    // TESTED, ABLE TO SEND BUT CANNOT SEE THE HEADERS IN THE LISTENER
//    private void sendByMessageChannel(final Greetings greetings)
//    {
//        MessageChannel messageChannel = greetingsStreams.outboundGreetings();
//        messageChannel.send(MessageBuilder
//                .withPayload(greetings)
////                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//                .setHeader("CustomHeader","CustomValue")
//                .build());
//    }

    private void sendByteArray(String topic, final Greetings greetings)
    {
        Message<byte[]> message = MessageBuilder
                .withPayload(greetings.toString().getBytes())
                .setHeader("Custom-header", "Custom-value")
                .build();

        MessageValues messageValues = new MessageValues(message);


        byte[] fullPayload = EmbeddedHeaderUtils.embedHeaders(messageValues, "Custom-header");

        kafkaTemplate.send(topic, fullPayload);
    }
}
