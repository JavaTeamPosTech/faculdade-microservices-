package com.postechfiap.faculdade.notificacao.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientEmailFunction {

    private final WebClient webClient;

    public WebClientEmailFunction(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void chamarFunction() {
        String resposta = webClient
                .get()
                .uri("https://frase-function-v2.azurewebsites.net/api/fraseFunction")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(resposta);
    }
}
