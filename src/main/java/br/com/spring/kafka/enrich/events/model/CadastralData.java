package br.com.spring.kafka.enrich.events.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table
public class CadastralData {

    @PrimaryKey
    private String nome;

    private String cpf;

    private String rg;

    private String sexo;
}
