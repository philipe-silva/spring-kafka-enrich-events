package br.com.spring.kafka.enrich.events.controller;

import br.com.spring.kafka.enrich.events.model.KafkaModelRequest;
import br.com.spring.kafka.enrich.events.service.SendRawEventsKafkaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.ServletContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SpringKafkaController.class)
class SpringKafkaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SendRawEventsKafkaService sendRawEventsKafkaService;

    @Test
    void testSendEventsToKafkaOk() throws Exception {
        Mockito.doReturn("OK").when(sendRawEventsKafkaService).sendEventsToKafka(Mockito.anyString());

        KafkaModelRequest kafkaModelRequest = new KafkaModelRequest();

        kafkaModelRequest.setNome("philipe");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sendToKafka")
                        .content(asJsonString(kafkaModelRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Enviado Com Sucesso!")));
    }

    @Test
    void testSendEventsToKafkaNOK() throws Exception {
        Mockito.doReturn("NOK").when(sendRawEventsKafkaService).sendEventsToKafka(Mockito.anyString());

        KafkaModelRequest kafkaModelRequest = new KafkaModelRequest();

        kafkaModelRequest.setNome("philipe");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sendToKafka")
                        .content(asJsonString(kafkaModelRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string(containsString("Erro ao Enviar para o Kafka")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}