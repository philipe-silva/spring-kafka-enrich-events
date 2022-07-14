package br.com.spring.kafka.enrich.events.repository;

import br.com.spring.kafka.enrich.events.model.CadastralData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Optional;
@EnableCassandraRepositories
public interface CadastralDataRepository extends CassandraRepository<CadastralData,String> {
    @Override
    Optional<CadastralData> findById(String nome);
}
