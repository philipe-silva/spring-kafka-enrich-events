package br.com.spring.kafka.enrich.events.service;

import br.com.spring.kafka.enrich.events.configuration.KafkaProducerConfig;
import br.com.spring.kafka.enrich.events.model.CadastralData;
import br.com.spring.kafka.enrich.events.model.KafkaModelRequest;
import br.com.spring.kafka.enrich.events.model.KafkaModelResponse;
import br.com.spring.kafka.enrich.events.repository.CadastralDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CassandraEnrichServiceTest {

    @Test
    void testProcessEvents() {
        //Arrange
        CassandraEnrichService cassandraEnrichService = new CassandraEnrichService();
        CassandraEnrichService cassandraEnrichServiceSpy = Mockito.spy(cassandraEnrichService);

        cassandraEnrichServiceSpy.kafkaProducerConfig = Mockito.mock(KafkaProducerConfig.class);

        Mockito.doNothing().when(cassandraEnrichServiceSpy.kafkaProducerConfig).sendMessage(Mockito.anyString(),Mockito.anyString());

        Mockito.doReturn(true).when(cassandraEnrichServiceSpy).enrichEvents(Mockito.any(KafkaModelResponse.class),Mockito.any(KafkaModelRequest.class));


        //Act
        String event = "{\"nome\" : \"philipe\"}";
        String returnValue = cassandraEnrichServiceSpy.processEvents(event);

        //Assert
        assertEquals("OK",returnValue,"Expected: OK");


    }

    @Test
    void testProcessEventsNOK() {
        //Arrange
        CassandraEnrichService cassandraEnrichService = new CassandraEnrichService();
        CassandraEnrichService cassandraEnrichServiceSpy = Mockito.spy(cassandraEnrichService);

        cassandraEnrichServiceSpy.kafkaProducerConfig = Mockito.mock(KafkaProducerConfig.class);

        Mockito.doNothing().when(cassandraEnrichServiceSpy.kafkaProducerConfig).sendMessage(Mockito.anyString(),Mockito.anyString());

        Mockito.doReturn(false).when(cassandraEnrichServiceSpy).enrichEvents(Mockito.any(KafkaModelResponse.class),Mockito.any(KafkaModelRequest.class));


        //Act
        String event = "{\"nome\" : \"philipe\"}";
        String returnValue = cassandraEnrichServiceSpy.processEvents(event);

        //Assert
        assertEquals("Não Encontrado",returnValue,"Expected: Não Encontrado");


    }

    @Test
    void testEnrichEventsOK() {
        //Arrange
        CassandraEnrichService cassandraEnrichService = new CassandraEnrichService();
        cassandraEnrichService.cadastralDataRepository = Mockito.mock(CadastralDataRepository.class);
        Optional<CadastralData> cadastralDataOptional = Mockito.mock(Optional.class);

        Mockito.doReturn(cadastralDataOptional).when(cassandraEnrichService.cadastralDataRepository).findById(Mockito.anyString());

        Mockito.doReturn(true).when(cadastralDataOptional).isPresent();

        CadastralData cadastralData = new CadastralData();
        cadastralData.setCpf("123.456.789-10");
        cadastralData.setNome("testeNome");
        cadastralData.setRg("12.345.678-9");
        cadastralData.setSexo("testeSexo");

        Mockito.doReturn(cadastralData).when(cadastralDataOptional).get();

        KafkaModelResponse kafkaModelResponse = new KafkaModelResponse();
        KafkaModelRequest kafkaModelRequest = new KafkaModelRequest();
        kafkaModelRequest.setNome("philipe");

        //Act
        Boolean isEnriched = cassandraEnrichService.enrichEvents(kafkaModelResponse, kafkaModelRequest);

        //Assert
        assertTrue(isEnriched,"Expected: True");

    }

    @Test
    void testEnrichEventsNOK() {
        //Arrange
        CassandraEnrichService cassandraEnrichService = new CassandraEnrichService();
        cassandraEnrichService.cadastralDataRepository = Mockito.mock(CadastralDataRepository.class);
        Optional<CadastralData> cadastralDataOptional = Mockito.mock(Optional.class);

        Mockito.doReturn(cadastralDataOptional).when(cassandraEnrichService.cadastralDataRepository).findById(Mockito.anyString());

        Mockito.doReturn(false).when(cadastralDataOptional).isPresent();


        KafkaModelResponse kafkaModelResponse = new KafkaModelResponse();
        KafkaModelRequest kafkaModelRequest = new KafkaModelRequest();
        kafkaModelRequest.setNome("philipe");

        //Act
        Boolean isEnriched = cassandraEnrichService.enrichEvents(kafkaModelResponse, kafkaModelRequest);

        //Assert
        assertFalse(isEnriched,"Expected: False");

    }

    @Test
    void testDateNow() {
    }
}