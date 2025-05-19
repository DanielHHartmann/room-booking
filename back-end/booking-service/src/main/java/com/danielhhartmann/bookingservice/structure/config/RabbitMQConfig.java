package com.danielhhartmann.bookingservice.structure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper; // Importar
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap; // Importar
import java.util.Map;    // Importar

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

        // Configuração do Type Mapper para desserialização
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("com.danielhhartmann.bookingservice.application.dto.roomService",
                "com.danielhhartmann.roomservice.application.dto.bookingService", // Adicionar pacote do DTO do room-service como confiável
                "java.util", "java.lang"); // Adicionar pacotes básicos

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        // Mapeia o __TypeId__ que vem do room-service para a classe local do booking-service
        idClassMapping.put(
                "com.danielhhartmann.roomservice.application.dto.bookingService.RoomStatusResponse", // Type ID enviado pelo room-service
                com.danielhhartmann.bookingservice.application.dto.roomService.RoomStatusResponse.class // Classe local no booking-service
        );
        typeMapper.setIdClassMapping(idClassMapping);

        converter.setJavaTypeMapper(typeMapper);
        // Manter setAlwaysConvertToInferredType para que o booking-service envie seu __TypeId__ corretamente
        // se ele precisar enviar objetos complexos como payload.
        // Neste caso, ele está enviando um Long, que não precisa de __TypeId__ complexo.
        // Mas para a resposta que ele recebe, o typeMapper acima resolverá.
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        // O timeout já está sendo setado via application.properties (spring.rabbitmq.template.reply-timeout)
        // template.setReplyTimeout(10000); // Ex: 10 segundos
        return template;
    }
}