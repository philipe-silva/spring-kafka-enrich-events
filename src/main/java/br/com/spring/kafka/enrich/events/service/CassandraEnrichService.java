package br.com.spring.kafka.enrich.events.service;

import br.com.spring.kafka.enrich.events.configuration.KafkaProducerConfig;
import br.com.spring.kafka.enrich.events.model.CadastralData;
import br.com.spring.kafka.enrich.events.model.KafkaModelRequest;
import br.com.spring.kafka.enrich.events.model.KafkaModelResponse;
import br.com.spring.kafka.enrich.events.repository.CadastralDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class CassandraEnrichService {

    @Autowired
    protected KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    protected CadastralDataRepository cadastralDataRepository;

    @Value("${spring.kafka.producer.topicOutput}")
    protected String topicOutput;

    public String processEvents(String event){

        GsonBuilder builder = new GsonBuilder().serializeNulls();

        Gson gson = builder.create();

        KafkaModelRequest kafkaModelRequest = gson.fromJson(event, KafkaModelRequest.class);


        String returnValue="";
        KafkaModelResponse kafkaModelResponse = new KafkaModelResponse();
        String responseString = "";

        Boolean enriched = enrichEvents(kafkaModelResponse, kafkaModelRequest);


        if(!enriched){
            returnValue = "Não Encontrado";

        }else{

            responseString = gson.toJson(kafkaModelResponse);
            kafkaProducerConfig.sendMessage(responseString,topicOutput);
            returnValue = "OK";
        }


        log.info("Evento Saida: {}",responseString);

        return returnValue;
    }

    protected Boolean  enrichEvents(KafkaModelResponse kafkaModelResponse,KafkaModelRequest kafkaModelRequest) {
        Optional<CadastralData> cadastralInformation = cadastralDataRepository.findById(kafkaModelRequest.getNome());

        if(cadastralInformation.isPresent()){

            CadastralData cadastralData = cadastralInformation.get();

            kafkaModelResponse.setNome(kafkaModelRequest.getNome());
            kafkaModelResponse.setDataProcessamento(dateNow());
            kafkaModelResponse.setCpf(cadastralData.getCpf());
            kafkaModelResponse.setRg(cadastralData.getRg());
            kafkaModelResponse.setSexo(cadastralData.getSexo());
            log.info("Evento Enriquecido com sucesso!");
            return true;

        }

        return false;
    }


    protected String dateNow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        return simpleDateFormat.format(now.getTime());

    }

}
