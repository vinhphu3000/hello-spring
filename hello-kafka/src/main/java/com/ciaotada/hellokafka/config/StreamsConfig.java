package com.ciaotada.hellokafka.config;

import com.ciaotada.hellokafka.stream.GreetingsStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(GreetingsStreams.class)
public class StreamsConfig {
}
