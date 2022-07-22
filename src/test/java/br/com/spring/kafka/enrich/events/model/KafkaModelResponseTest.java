package br.com.spring.kafka.enrich.events.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KafkaModelResponseTest {

    @Test
    public void testGettersAndSetters(){
        KafkaModelResponse kafkaModelResponse = new KafkaModelResponse();
        kafkaModelResponse.setSexo("testSexo");
        kafkaModelResponse.setRg("testRG");
        kafkaModelResponse.setCpf("testCPF");
        kafkaModelResponse.setNome("testNome");
        kafkaModelResponse.setDataProcessamento("testDataProcessamento");


        assertEquals("testSexo",kafkaModelResponse.getSexo(),"Expected: testSexo");
        assertEquals("testRG",kafkaModelResponse.getRg(),"Expected: testRG");
        assertEquals("testCPF",kafkaModelResponse.getCpf(),"Expected: testCPF");
        assertEquals("testNome",kafkaModelResponse.getNome(),"Expected: testNome");
        assertEquals("testDataProcessamento",kafkaModelResponse.getDataProcessamento(),"Expected: testDataProcessamento");

    }

    @Test
    public void testOtherMethods(){
        KafkaModelResponse kafkaModelResponse = new KafkaModelResponse();
        kafkaModelResponse.setCpf("testCPF");
        KafkaModelResponse kafkaModelResponse1 = new KafkaModelResponse();

        int hashCode = 1676876842;
        assertTrue(kafkaModelResponse.canEqual(kafkaModelResponse1),"Expected: True");
        assertFalse(kafkaModelResponse.equals(kafkaModelResponse1),"Expected: False");
        assertEquals(hashCode,kafkaModelResponse.hashCode(),"Expected: 1676876842");
        assertEquals("KafkaModelResponse(nome=null, dataProcessamento=null, cpf=testCPF, rg=null, sexo=null)",
                kafkaModelResponse.toString(),
                "Expected: KafkaModelResponse(nome=null, dataProcessamento=null, cpf=testCPF, rg=null, sexo=null)");

    }


}