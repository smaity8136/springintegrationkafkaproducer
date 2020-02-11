package com.pro.test.kafka.demoproducer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestKafkaProducerConfig {

    @Value("${kafka.brokers}")
    private String kafkaBrokers;

    @Autowired
    private MessageChannel testErrorChannel;

    private String topic = "testtopic";

    private String errorTopic = "errortopic";

    @Bean
    public Map<String, Object> getKafkaConfig() {
        Map<String, Object> configProps = new HashMap<String, Object>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "test-kafka-producer");
        return configProps;
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new DefaultKafkaProducerFactory<>(getKafkaConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    //@ServiceActivator(inputChannel = "producerChannel")
    public MessageHandler kafkaMessageHandler() {
        KafkaProducerMessageHandler handler = new KafkaProducerMessageHandler(kafkaTemplate());
        handler.setSendFailureChannel(testErrorChannel);
        handler.setTopicExpression(new LiteralExpression("testtopic"));
        return handler;
    }


}
