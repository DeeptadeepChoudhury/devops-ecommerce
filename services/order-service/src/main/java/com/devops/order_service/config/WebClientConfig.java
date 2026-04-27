package com.devops.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.slf4j.MDC;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter((request, next) -> {
                    String correlationId = MDC.get("X-Correlation-ID");

                    return next.exchange(
                            ClientRequest.from(request)
                                    .header("X-Correlation-ID", correlationId)
                                    .build()
                    );
                })
                .build();
    }
}