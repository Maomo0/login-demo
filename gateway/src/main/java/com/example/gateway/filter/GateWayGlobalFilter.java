package com.example.gateway.filter;

import com.example.gateway.feign.LoginFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-31 16:38
 * @className: GateWayGlobalFilter
 * @description:
 **/
//@Configuration 和@Bean一起才会生效
    //@component 过滤器不会生效
public class GateWayGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(GateWayGlobalFilter.class);
    @Resource
    private LoginFeign  loginFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        String host = request.getURI().getHost();
        String method = Objects.requireNonNull(request.getMethod()).name();
        int port = request.getURI().getPort();

        String scheme = request.getURI().getScheme();
        String basePath = scheme + "://" + host + ":" + port;
        LOGGER.info("访问地址:" + (basePath + path));
        if ("/api/auth/login".equals(path)){
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("Authorization");
        String sid = request.getHeaders().getFirst("sid");
        loginFeign.refreshToken(token);
//        if(!Objects.equals(result.getCode(), 200)){
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            DataBuffer dataBuffer = response.bufferFactory().wrap(result.toString().getBytes(StandardCharsets.UTF_8));
//            return response.writeWith(Mono.just(dataBuffer));
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
