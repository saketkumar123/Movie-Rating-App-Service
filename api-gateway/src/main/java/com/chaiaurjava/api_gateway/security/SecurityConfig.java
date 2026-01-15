package com.chaiaurjava.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        .pathMatchers("/actuator/**").permitAll()

                        .anyExchange().access((authentication, authorizationContext) -> {

                            String header =
                                    authorizationContext
                                            .getExchange()
                                            .getRequest()
                                            .getHeaders()
                                            .getFirst("X-Internal-Call");

                            return Mono.just(new AuthorizationDecision(header != null));
                        })
                )
                .build();
    }
}

