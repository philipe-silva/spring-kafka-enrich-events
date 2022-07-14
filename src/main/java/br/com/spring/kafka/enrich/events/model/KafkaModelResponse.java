package br.com.spring.kafka.enrich.events.model;

import lombok.Data;

@Data
public class KafkaModelResponse {
    protected String nome;

    protected String dataProcessamento;

    protected String cpf;

    protected String rg;

    protected String sexo;
}
