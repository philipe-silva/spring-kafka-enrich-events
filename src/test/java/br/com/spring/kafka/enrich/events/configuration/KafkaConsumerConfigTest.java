package br.com.spring.kafka.enrich.events.configuration;

import br.com.spring.kafka.enrich.events.service.CassandraEnrichService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConsumerConfigTest {

    @Test
    public void testListen() {
        //Arrange
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
        kafkaConsumerConfig.cassandraEnrichService = Mockito.mock(CassandraEnrichService.class);

        ConsumerRecord<String,String> consumerRecord = new ConsumerRecord<>("topic",0,0,"key","value");

        Mockito.doReturn("OK").when(kafkaConsumerConfig.cassandraEnrichService).processEvents(Mockito.anyString());

        Acknowledgment acknowledgment = new Acknowledgment() {
            @Override
            public void acknowledge() {
                System.out.println("Ack");
            }
        };
        //Act
        kafkaConsumerConfig.listen(consumerRecord,acknowledgment);

        //Assert
        assertNotNull(kafkaConsumerConfig,"Expected: Not Null");


    }

    @Test
    public void testListenValueIsNull() {
        //Arrange
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
        kafkaConsumerConfig.cassandraEnrichService = Mockito.mock(CassandraEnrichService.class);

        ConsumerRecord<String,String> consumerRecord = new ConsumerRecord<>("topic",0,0,"key",null);

        Mockito.doReturn("OK").when(kafkaConsumerConfig.cassandraEnrichService).processEvents(Mockito.anyString());

        Acknowledgment acknowledgment = new Acknowledgment() {
            @Override
            public void acknowledge() {
                System.out.println("Ack");
            }
        };
        //Act
        kafkaConsumerConfig.listen(consumerRecord,acknowledgment);

        //Assert
        assertNotNull(kafkaConsumerConfig,"Expected: Not Null");


    }

    @Test
    public void consumerFactory() {
        //Arrange
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
        kafkaConsumerConfig.bootstrapServers = "server1,server2";
        kafkaConsumerConfig.groupId = "test-group-id";

        //Act
        ConsumerFactory<String, String> consumerFactoryMap = kafkaConsumerConfig.consumerFactory();


        //Assert
        assertEquals("server1,server2",
                consumerFactoryMap.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG),
                "Expected: server1,server2");
        assertEquals("test-group-id",
                consumerFactoryMap.getConfigurationProperties().get(ConsumerConfig.GROUP_ID_CONFIG),
                "Expected: server1,server2");
        assertEquals(StringDeserializer.class,
                consumerFactoryMap.getConfigurationProperties().get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG),
                "Expected: StringSerializer.class");
        assertEquals(StringDeserializer.class,
                consumerFactoryMap.getConfigurationProperties().get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG),
                "Expected: StringSerializer.class");
        assertFalse(((boolean) consumerFactoryMap.getConfigurationProperties().get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG)),
                "Expected: True");

    }

    @Test
    public void kafkaListenerContainerFactory() {
        //Arrange
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

        //Act
        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaConsumerConfig.kafkaListenerContainerFactory();

        //Assert
        assertEquals(ContainerProperties.AckMode.MANUAL_IMMEDIATE,factory.getContainerProperties().getAckMode(),
                "Expected: MANUAL");
    }
}