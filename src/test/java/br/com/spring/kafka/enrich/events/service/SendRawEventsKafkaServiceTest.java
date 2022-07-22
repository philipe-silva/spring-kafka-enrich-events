package br.com.spring.kafka.enrich.events.service;

import br.com.spring.kafka.enrich.events.configuration.KafkaProducerConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SendRawEventsKafkaServiceTest {

    @Test
    void testSendEventsToKafkaOk() {
        //Arrange
        SendRawEventsKafkaService sendRawEventsKafkaService = new SendRawEventsKafkaService();
        sendRawEventsKafkaService.kafkaProducerConfig = Mockito.mock(KafkaProducerConfig.class);

        Mockito.doNothing().when(sendRawEventsKafkaService.kafkaProducerConfig).sendMessage(Mockito.anyString(),Mockito.anyString());

        //Act
        String returnValue = sendRawEventsKafkaService.sendEventsToKafka("{\"nome\" : \"philipe\"}");

        //Assert
        assertEquals("OK",returnValue,"Expected: OK");

    }

    @Test
    void testSendEventsToKafkaNOK() {
        //Arrange
        SendRawEventsKafkaService sendRawEventsKafkaService = new SendRawEventsKafkaService();

        //Act
        String returnValue = sendRawEventsKafkaService.sendEventsToKafka("{\"nome\" : \"philipe\"}");

        //Assert
        assertEquals("Error",returnValue,"Expected: Error");

    }
}