package br.com.spring.kafka.enrich.events.controller;

import br.com.spring.kafka.enrich.events.service.SpringKafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringKafkaController {


    @Autowired
    protected SpringKafkaService springKafkaService;

    @PostMapping(path = "/sendToKafka")
    public ResponseEntity sendEventsToKafka(@RequestBody String event) {
        String returnService = springKafkaService.sendEventToKafka(event);
        ResponseEntity responseFinal = null;
        if (returnService.equalsIgnoreCase("OK")) {
            responseFinal = new ResponseEntity("Enviado Com Sucesso!", HttpStatus.OK);
        } else {
            responseFinal = new ResponseEntity("Não Aceito", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseFinal;
    }

}
