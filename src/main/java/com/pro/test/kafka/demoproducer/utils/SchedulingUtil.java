package com.pro.test.kafka.demoproducer.utils;

import com.pro.test.kafka.demoproducer.gateway.TestProducerGateWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
public class SchedulingUtil {

    private TestProducerGateWay testProducerGateWay;

    @Scheduled(cron = "* */5 * * * *")
    public void startTestProducer() {

        this.testProducerGateWay.msgProduceToKafka("start");
    }


    @Autowired
    public void setTestProducerGateWay(TestProducerGateWay testProducerGateWay) {
        this.testProducerGateWay = testProducerGateWay;
    }
}
