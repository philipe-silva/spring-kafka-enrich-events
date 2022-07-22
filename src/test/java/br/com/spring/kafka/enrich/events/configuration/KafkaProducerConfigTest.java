package br.com.spring.kafka.enrich.events.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerConfigTest {

    @Test
    void testProducerConfigs() {
        //Arrange
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        kafkaProducerConfig.bootstrapServers = "server1;server2";

        //Act
        Map<String, Object> map = kafkaProducerConfig.producerConfigs();

        //Assert
        assertEquals("server1;server2",map.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG),
                "Expected: server1;server2");
        assertEquals(StringSerializer.class,map.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG),
                "Expected: StringSerializer.class");
        assertEquals(StringSerializer.class,map.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG),
                "Expected: StringSerializer.class");
        assertEquals(1,map.get(ProducerConfig.BATCH_SIZE_CONFIG),
                "Expected: 1");
        assertEquals("1",map.get(ProducerConfig.ACKS_CONFIG),
                "Expected: 1");


    }

    @Test
    void testInitTemplate() {
        //Arrange
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        KafkaTemplate<String,String> kafkaTemplate = null;

        //Act
        kafkaTemplate = kafkaProducerConfig.initTemplate(kafkaTemplate);

        //Assert
        assertNotNull(kafkaTemplate,"Expected: Not Null");
    }

    @Test
    void testSendMessage() {
        //Arrange
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        KafkaProducerConfig kafkaProducerConfigSpy = Mockito.spy(kafkaProducerConfig);

        kafkaProducerConfigSpy.kafkaTemplate = Mockito.mock(KafkaTemplate.class);

        Mockito.doReturn(kafkaProducerConfigSpy.kafkaTemplate).when(kafkaProducerConfigSpy).initTemplate(Mockito.any(KafkaTemplate.class));

        ListenableFuture<SendResult<String, String>> future = Mockito.mock(ListenableFuture.class);

        Mockito.doReturn(future).when(kafkaProducerConfigSpy.kafkaTemplate).send(Mockito.anyString(),Mockito.anyString());


        //Act
        kafkaProducerConfigSpy.sendMessage("test","testTopic");


        //Assert
        assertNotNull(kafkaProducerConfig,"Expected: Not null");

    }

}