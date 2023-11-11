package dev.bayun.api.forms.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Максим Яськов
 */
@Configuration
public class WebClientConfiguration {

//    @Bean
//    @Profile("dev")
//    public WebClient devWebClient(WebClientSsl webClientSsl, WebClient.Builder builder) {
//        return builder.apply(webClientSsl.fromBundle("ssl-server")).build();
//    }

    @Bean
    @LoadBalanced
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
//    @Profile("!dev")
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

}
