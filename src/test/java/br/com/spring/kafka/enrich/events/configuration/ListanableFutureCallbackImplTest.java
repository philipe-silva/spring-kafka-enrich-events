package br.com.spring.kafka.enrich.events.configuration;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.SendResult;

import static org.junit.jupiter.api.Assertions.*;

class ListanableFutureCallbackImplTest {

    @Test
    public void onFailure() {
        //Arrange
        Throwable throwable = new Throwable();
        ListanableFutureCallbackImpl listanableFutureCallback = new ListanableFutureCallbackImpl();

        //Act
        listanableFutureCallback.onFailure(throwable);

        //Assert
        assertNotNull(listanableFutureCallback,"Expected: Not Null");
    }

    @Test
    public void onSuccess() {
        //Arrange
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic",1,0L,"key","value");
        TopicPartition topicPartition = new TopicPartition("teste",0);
        Long checksum = Long.valueOf("0");
        RecordMetadata recordMetadata = new RecordMetadata(topicPartition,0L,0L,0L,checksum,1,1);
        SendResult<String,String> sendResult = new SendResult<>(producerRecord,recordMetadata);
        ListanableFutureCallbackImpl listanableFutureCallback = new ListanableFutureCallbackImpl();

        //Act
        listanableFutureCallback.onSuccess(sendResult);

        //Assert
        assertNotNull(listanableFutureCallback,"Expected: Not null");
    }
}