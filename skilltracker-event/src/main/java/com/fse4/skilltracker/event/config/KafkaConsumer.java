package com.fse4.skilltracker.event.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse4.skilltracker.event.service.SkillTrackerEventService;

import lombok.extern.slf4j.Slf4j;



/**
 * This is the Kafka Consumer Class that that will listen to the Kafka Queue for incoming messages.
 * The Incoming messages will be the JSON entity Object
 *
 */
@Slf4j
@Service
public class KafkaConsumer {

    //private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private SkillTrackerEventService service;
    
    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
            ,containerFactory="kafkaListenerContainerFactory"
    )


    public void consume(@Payload String kafkaMessage){
//    public void consume(KafkaMessage kafkaMessage){    
    	
        log.info(String.format("Course event received in course-query service => %s", kafkaMessage.toString()));
		KafkaMessage messagedetails = null;
		try {
			messagedetails = new ObjectMapper().readValue(kafkaMessage, KafkaMessage.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        System.out.println("Kafka Consumer event++++++++++++++++++ messagedetails : "+messagedetails);
       if(messagedetails.getProfile() !=null) {
    	   service.syncProfileDetailsToQueryDatabse(messagedetails);
       } 
       if(messagedetails.getSkills() !=null) {
      		service.syncSkillDetailsToQueryDatabase(messagedetails);    	   
       }       
			
    }

}