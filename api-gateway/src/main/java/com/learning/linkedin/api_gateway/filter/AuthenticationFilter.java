package com.learning.linkedin.api_gateway.filter;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JWTService jwtService;

    public AuthenticationFilter(JWTService jwtService)
    {
        super(Config.class);
        this.jwtService = new JWTService();
    }

    @Override
    public GatewayFilter apply(Config config)
    {

        return (((exchange, chain) -> {
            //log.info("Login Request: {}",exchange.getRequest().getURI());
            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(tokenHeader == null || !tokenHeader.startsWith("Bearer"))
            {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               // log.error("Auth token header not found");
                return exchange.getResponse().setComplete();
            }
            final String token = tokenHeader.split("Bearer ")[1];



                String userId = jwtService.getUserIdFromToken(token);
                ServerWebExchange serverWebExchange = exchange.mutate()
                        .request(r -> r.header("X-User-Id", userId))
                        .build();
                return chain.filter(serverWebExchange);


        }));
    }

    public static class Config{

    }

}
