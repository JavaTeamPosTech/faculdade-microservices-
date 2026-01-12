package com.postechfiap.faculdade.notificacao.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientEmailFunction {

    private final WebClient webClient;

    public WebClientEmailFunction(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void chamarFunction(com.postechfiap.meuhospital.dto.AvaliacaoCriadaEvent event) {
        EmailDTO dto = new EmailDTO();
        dto.setEmailDestino(event.emailProfessor());
        dto.setAssunto("Avalição do aluno");
        dto.setCorpo(event.descricao());
        System.out.println("Chamando Azure Function para enviar email...");
        String endpoint = System.getenv("APPSETTING_URL_FUNCTION_ENVIAR_EMAIL");
        String resposta = webClient
                .post()
                .uri(endpoint)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("Retorno da Azure Function de enviar email...");
        System.out.println(resposta);
    }


    public static class EmailDTO {
        private String emailDestino;
        private String assunto;
        private String corpo;
        private String alunoNome;
        private String comentario;
        private String prioridade = "Urgente";
        private int nota;

        public String getEmailDestino() {
            return emailDestino;
        }

        public void setEmailDestino(String emailDestino) {
            this.emailDestino = emailDestino;
        }

        public String getAssunto() {
            return assunto;
        }

        public void setAssunto(String assunto) {
            this.assunto = assunto;
        }

        public String getCorpo() {
            return corpo;
        }

        public void setCorpo(String corpo) {
            this.corpo = corpo;
        }

        public String getAlunoNome() {
            return alunoNome;
        }

        public void setAlunoNome(String alunoNome) {
            this.alunoNome = alunoNome;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public String getPrioridade() {
            return prioridade;
        }

        public void setPrioridade(String prioridade) {
            this.prioridade = prioridade;
        }

        public int getNota() {
            return nota;
        }

        public void setNota(int nota) {
            this.nota = nota;
        }
    }


}


