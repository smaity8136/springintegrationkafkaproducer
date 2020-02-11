package com.pro.test.kafka.demoproducer.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway(errorChannel = "testErrorChannel")
public interface TestProducerGateWay {
    @Gateway(requestChannel = "testProducerStart")
    public void msgProduceToKafka(String st);
}
