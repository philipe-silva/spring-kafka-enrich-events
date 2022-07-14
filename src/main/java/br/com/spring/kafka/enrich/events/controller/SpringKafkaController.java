package br.com.spring.kafka.enrich.events.controller;

import br.com.spring.kafka.enrich.events.service.CassandraEnrichService;
import br.com.spring.kafka.enrich.events.service.SendRawEventsKafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringKafkaController {


    @Autowired
    protected SendRawEventsKafkaService sendRawEventsKafkaService;

    @PostMapping(path = "/sendToKafka")
    public ResponseEntity sendEventsToKafka(@RequestBody String event) {
        String returnService = sendRawEventsKafkaService.sendEventsToKafka(event);
        ResponseEntity responseFinal = null;
        if (returnService.equalsIgnoreCase("OK")) {
            responseFinal = new ResponseEntity("Enviado Com Sucesso!", HttpStatus.OK);
        } else {
            responseFinal = new ResponseEntity("Erro ao Enviar para o Kafka", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseFinal;
    }

}
