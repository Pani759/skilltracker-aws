package com.fse4.skilltracker.event.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerProfile {

    //private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerProfile.class);

    private NewTopic topic;

    private KafkaTemplate<String, KafkaMessage> skillTrackerKafkaTemplate;

    public KafkaProducerProfile(NewTopic topic, KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.topic = topic;
        this.skillTrackerKafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(KafkaMessage kafkaMessage){
        log.info(String.format("KAKFA MESSAGE SENT OUT IS => %s", kafkaMessage.toString()));
        log.info("KAKFA MESSAGE SENT OUT IS => %s" + kafkaMessage.toString());

        // create Message
        Message<KafkaMessage> message = MessageBuilder
                .withPayload(kafkaMessage)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        skillTrackerKafkaTemplate.send(message);
    }
    
	/*
	 * public void sendMessage(KafkaSkillMessage kafkaSkillMessage){
	 * log.info(String.format("KAKFA MESSAGE SENT OUT IS => %s",
	 * kafkaSkillMessage.toString())); log.info("KAKFA MESSAGE SENT OUT IS => %s" +
	 * kafkaSkillMessage.toString());
	 * 
	 * // create Message Message<KafkaSkillMessage> message = MessageBuilder
	 * .withPayload(kafkaSkillMessage) .setHeader(KafkaHeaders.TOPIC, topic.name())
	 * .build(); skillTrackerKafkaTemplate.send(message); }
	 */
}
