package com.pro.test.kafka.demoproducer.utils;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KafkaUtil {

    public Message msgGenerator() {
        String[] randomWords = new String[]{"foo", "bar", "foobar", "baz", "fox"};
        Random random = new Random();
        int idx = random.nextInt(5);
        String msg = randomWords[idx];
        return sendMsgToOutputChannel(idx, msg);
    }


    private Message sendMsgToOutputChannel(int idx, String msg) {

        Message smsg = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.MESSAGE_KEY, idx)//String.valueOf(idx))
                .build();

        return smsg;
    }

    public void processError(Message<?> message) {
        System.out.println("Handling ERROR: " + message);
    }
}
