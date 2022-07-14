package br.com.spring.kafka.enrich.events.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrapServers}")
    protected String bootstrapServers;



    protected KafkaTemplate<String,String> kafkaTemplate;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,1);
        props.put(ProducerConfig.ACKS_CONFIG,"1");
        return props;
    }

    protected KafkaTemplate<String,String> initTemplate(KafkaTemplate<String,String> kafkaTemplate){
        if(kafkaTemplate == null) {
            kafkaTemplate = kafkaTemplate();
        }
        return kafkaTemplate;

    }

    public void sendMessage(String msg,String topic){
        kafkaTemplate = initTemplate(kafkaTemplate);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic,msg);

        future.addCallback(getInstanceListanable());
    }


    protected ListanableFutureCallbackImpl getInstanceListanable(){
        return new ListanableFutureCallbackImpl();
    }


    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }


    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
