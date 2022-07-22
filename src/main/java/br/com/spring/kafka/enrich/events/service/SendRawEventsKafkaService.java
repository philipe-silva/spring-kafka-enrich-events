package br.com.spring.kafka.enrich.events.service;

import br.com.spring.kafka.enrich.events.configuration.KafkaProducerConfig;
import br.com.spring.kafka.enrich.events.model.KafkaModelRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendRawEventsKafkaService {

    @Autowired
    protected KafkaProducerConfig kafkaProducerConfig;

    @Value("${spring.kafka.producer.topic}")
    protected String topic;

    public String sendEventsToKafka(String event){
        try {
        log.info("Evento: {} ",event);
        GsonBuilder builder = new GsonBuilder().serializeNulls();

        Gson gson = builder.create();

        KafkaModelRequest kafkaModelRequest = gson.fromJson(event, KafkaModelRequest.class);

        String jsonFormatted = gson.toJson(kafkaModelRequest);

            kafkaProducerConfig.sendMessage(jsonFormatted, topic);

        }catch (Exception e){
            e.printStackTrace();
            log.error("Erro ao Enviar para o Kafka: {}",e.getMessage());
            return "Error";
        }
        return "OK";
    }
}
