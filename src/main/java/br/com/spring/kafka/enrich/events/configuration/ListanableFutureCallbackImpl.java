package br.com.spring.kafka.enrich.events.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
@Slf4j
public class ListanableFutureCallbackImpl implements ListenableFutureCallback<SendResult<String,String>> {

    @Override
    public void onFailure(Throwable throwable)   {
        throwable.printStackTrace();
        log.error("Erro ao Enviar mensagem: {}",throwable.getMessage());
    }

    @Override
    public void onSuccess(SendResult<String,String> result) {
        log.info("Mensagem [ {} ] enviada com sucesso ao Kafka",result.getProducerRecord().value());
    }
}
