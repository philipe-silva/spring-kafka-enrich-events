package br.com.spring.kafka.enrich.events.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CadastralDataTest {

    @Test
    public void testGettersAndSetters(){
        CadastralData cadastralData = new CadastralData();
        cadastralData.setCpf("123.456.789-10");
        cadastralData.setNome("testeNome");
        cadastralData.setRg("12.345.678-9");
        cadastralData.setSexo("testeSexo");


        assertEquals("123.456.789-10",cadastralData.getCpf());
        assertEquals("testeNome",cadastralData.getNome());
        assertEquals("12.345.678-9",cadastralData.getRg());
        assertEquals("testeSexo",cadastralData.getSexo());
    }

    @Test
    public void testOtherMethods() {
        CadastralData cadastralData = new CadastralData();

        cadastralData.setSexo("testeSexo");
        CadastralData cadastralData1 = new CadastralData();


        int hashCode=-1154463030;
        assertTrue(cadastralData.canEqual(cadastralData1),"Expected: true");
        assertFalse(cadastralData.equals(cadastralData1),"Expected: False");
        assertEquals(hashCode,cadastralData.hashCode(),"Expected: -1154463030");
        assertEquals("CadastralData(nome=null, cpf=null, rg=null, sexo=testeSexo)",
                cadastralData.toString(),
                "Expected: CadastralData(nome=null, cpf=null, rg=null, sexo=testeSexo)");

    }

}