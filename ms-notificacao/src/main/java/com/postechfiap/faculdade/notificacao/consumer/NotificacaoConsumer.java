package com.postechfiap.faculdade.notificacao.consumer;

import com.postechfiap.faculdade.notificacao.client.WebClientEmailFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumidor Kafka responsável por processar eventos de consultas e simular o envio de notificações.
 */
@Component
public class NotificacaoConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoConsumer.class);
    private static final String NOTIFICACAO_TOPIC = "notificacao-events";
    private final WebClientEmailFunction webClientEmailFunction;


    public NotificacaoConsumer(WebClientEmailFunction webClientEmailFunction){
        this.webClientEmailFunction = webClientEmailFunction;
    }

    /**
     * Listener que consome eventos de atualização/criação de consultas.
     */
    @KafkaListener(
            topics = "${app.kafka.topic-notificacao}",
            groupId = "notificacao-group"
    )
    public void consume(com.postechfiap.meuhospital.dto.AvaliacaoCriadaEvent event) {

        log.info("--- EVENTO RECEBIDO NO MS-NOTIFICACAO ---");
        log.info("Processando notificação para a consulta ID: {}", event.idAvaliacao());
        System.out.println("Evento AvaliacaoCriadaEvent recebido: " + event);

        try {
            //Se a nota for positiva, não envia
            if(event.nota() < 6){
                //chamando azure function
                webClientEmailFunction.chamarFunction(event);
            }
        } catch (Exception e) {
            log.error("Erro FATAL ao processar evento de notificação para a Avaliação ID {}. O log será salvo com status 'FALHA'.", event.idCurso(), e);
        }
    }
}