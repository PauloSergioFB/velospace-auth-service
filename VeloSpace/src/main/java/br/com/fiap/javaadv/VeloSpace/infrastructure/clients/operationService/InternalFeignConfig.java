package br.com.fiap.javaadv.VeloSpace.infrastructure.clients.operationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class InternalFeignConfig {

    @Bean
    RequestInterceptor internalApiKeyInterceptor() {
        return template -> template.header("X-Internal-Api-Key", "${internal.api-key}");
    }

}
