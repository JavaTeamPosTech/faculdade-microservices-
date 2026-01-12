package com.faculdade.ms_cursos.kafka;

import com.postechfiap.meuhospital.dto.AvaliacaoCriadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Produtor Kafka responsável por enviar eventos de avaliacao (criação)
 * para o tópico de notificação.
 */
@Component
public class AvaliacaoProducer {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoProducer.class);

    @Value("${app.kafka.topic-notificacao}")
    private String notificacaoTopic;

    private final KafkaTemplate<String, AvaliacaoCriadaEvent> kafkaTemplate;

    public AvaliacaoProducer(KafkaTemplate<String, AvaliacaoCriadaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publica um evento de avaliacao no Kafka.
     * * @param event O DTO de evento (AvaliacaoCriadaEvent) com os dados e tipo de evento.
     */
    public void sendAvaliacaoEvent(AvaliacaoCriadaEvent event) {
        String key = event.idAvaliacao().toString();

        kafkaTemplate.send(notificacaoTopic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("KAFKA SUCESSO: Evento Avaliacao [{}] ID [{}] publicado no curso {}.",
                                event.idCurso(), key, notificacaoTopic);
                    } else {
                        log.error("KAFKA FALHA: Erro ao publicar evento Avaliacao [{}] ID [{}] no curso {}.",
                                event.idCurso(), key, notificacaoTopic, ex);
                    }
                });
    }
}