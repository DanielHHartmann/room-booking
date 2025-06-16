package com.danielhhartmann.bookingservice.structure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "room-service-queue";
    public static final String EXCHANGE_NAME = "room-service-exchange";
    public static final String ROUTING_KEY = "room.service.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("com.danielhhartmann.bookingservice.application.dto.roomService",
                "com.danielhhartmann.roomservice.application.dto.bookingService",
                "java.util", "java.lang");

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(
                "com.danielhhartmann.roomservice.application.dto.bookingService.RoomStatusResponse",
                com.danielhhartmann.bookingservice.application.dto.roomService.RoomStatusResponse.class
        );
        typeMapper.setIdClassMapping(idClassMapping);

        converter.setJavaTypeMapper(typeMapper);

        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);

        return template;
    }
}