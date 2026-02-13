package com.Apigateway.ApiGateway.security;

import com.example.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    //I am creating my own custom filter for Spring Cloud Gateway.”
    //Spring Cloud Gateway already knows:
    //how to intercept requests
    //how to stop or allow them
    //You’re just adding your own rules.


    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }
    @Override
    public GatewayFilter apply(Config config) {
        System.out.println("GATEWAY FILTER HIT");
        //This method runs for every request passing through the gateway.

        return (exchange, chain) -> {
            String authHeader =
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            System.out.println("AUTH HEADER = " + authHeader);
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return unauthorized(exchange);
            } //If the request does NOT contain an Authorization header → STOP”

             //Authorization: Bearer xyz123

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }
//            Invalid:
//            Missing “Bearer”
//            Random text
//            Empty

            String token = authHeader.substring(7);  //Extracting the JWT token
            System.out.println("JWT TOKEN = " + token);


            try {
                Claims claims = jwtUtil.validateToken(token);
                System.out.println("JWT VALID for user: " + claims.getSubject());
            } catch (Exception e) {
                System.out.println("JWT ERROR >>> " + e.getClass().getName());
                System.out.println("JWT MESSAGE >>> " + e.getMessage());
                return unauthorized(exchange);
            }//it checks if token is valid. call JwtUtil.validateToken(token)

            String username = jwtUtil.extractUsername(token);   // extract username + role
            List<String> roles = Collections.singletonList(jwtUtil.extractRole(token));

//            roles (USER / ADMIN)
            String path = exchange.getRequest().getURI().getPath(); //extracts path
            if (path.startsWith("/orders") && !roles.contains("USER")) { //check if path starts with /orders
                return forbidden(exchange);
            }

            if (path.startsWith("/products") &&
                    !(roles.contains("USER") || roles.contains("ADMIN"))) {
                return forbidden(exchange);
            }
            exchange = exchange.mutate()
                    .request(
                            exchange.getRequest().mutate()
                                    .header("X-User-Role", jwtUtil.extractRole(token))
                                    .build()
                    )
                    .build();

            return chain.filter(exchange);
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
   }
   //HTTP 401 Unauthorized
//    Meaning:“You are not allowed. Please login
   private Mono<Void> forbidden(ServerWebExchange exchange) {
       exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
       return exchange.getResponse().setComplete();
   }

    public static class Config {
    }
//    Spring Cloud Gateway does this internally:
//    Reads route configuration from application.propoerties
//    Binds properties under your filter name
//    Injects them into the Config object
//    Passes it to apply(Config config)
//    So when this runs:
//            .apply(Config config)
//    Spring has already populated it.
}
