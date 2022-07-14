package br.com.spring.kafka.enrich.events.service;

import br.com.spring.kafka.enrich.events.configuration.ConfigKafka;
import br.com.spring.kafka.enrich.events.model.KafkaModelRequest;
import br.com.spring.kafka.enrich.events.model.KafkaModelResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Slf4j
public class SpringKafkaService {

    @Autowired
    protected ConfigKafka configKafka;

    public String sendEventToKafka(String event){
        log.info("Evento: {}",event);

        GsonBuilder builder = new GsonBuilder().serializeNulls();

        Gson gson = builder.create();

        KafkaModelRequest kafkaModelRequest = gson.fromJson(event, KafkaModelRequest.class);

        String type = kafkaModelRequest.getType();

        String returnValue="";
        KafkaModelResponse kafkaModelResponse = new KafkaModelResponse();
        String responseString = "";
        if(type.equalsIgnoreCase("Ignorar")){
            returnValue = "Ignorar";
        }else{

            kafkaModelResponse.setNome(kafkaModelRequest.getNome());
            kafkaModelResponse.setDataProcessamento(dateNow());
            kafkaModelResponse.setType(kafkaModelRequest.getType());
            responseString = gson.toJson(kafkaModelResponse);
            configKafka.sendMessage(responseString);
            returnValue = "OK";
        }



        log.info("Evento Saida: {}",responseString);

        return returnValue;
    }


    protected String dateNow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        String dataFormatada = simpleDateFormat.format(now.getTime());
        return dataFormatada;

    }

}
