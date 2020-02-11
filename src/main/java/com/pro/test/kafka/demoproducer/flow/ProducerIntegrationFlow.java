package com.pro.test.kafka.demoproducer.flow;


import com.pro.test.kafka.demoproducer.utils.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
@EnableIntegration
public class ProducerIntegrationFlow {
    @Autowired
    private KafkaUtil kUtil;

    @Autowired
    private MessageHandler kafkaMessageHandler;

    @Bean
    public IntegrationFlow kafkaTestProducerflow() throws Exception {
        return IntegrationFlows.from("testProducerStart")
                .handle(kUtil,"msgGenerator")
                .log(LoggingHandler.Level.INFO)
                .split()
                .handle(kafkaMessageHandler)
                .get();
    }

    @Bean
    public IntegrationFlow kafkaErrorProducerflow() throws Exception {
        return IntegrationFlows.from("testErrorChannel")
                .handle(kUtil,"processError")
                .log(LoggingHandler.Level.INFO)
                .get();
    }



}
