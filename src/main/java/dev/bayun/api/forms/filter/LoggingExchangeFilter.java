package dev.bayun.api.forms.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Максим Яськов
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingExchangeFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("{} {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath());
        exchange.getRequest().getHeaders().forEach((header, values) ->
                log.info("{}: {}",  header, String.join(", ", values)));
        return chain.filter(exchange);
    }
}
