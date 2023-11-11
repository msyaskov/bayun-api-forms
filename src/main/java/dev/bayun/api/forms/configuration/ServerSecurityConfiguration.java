package dev.bayun.api.forms.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.DelegatingServerAuthenticationEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

/**
 * @author Максим Яськов
 */
@Configuration
@RequiredArgsConstructor
public class ServerSecurityConfiguration {

    public final ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(configurer -> configurer
                .pathMatchers("/login/**", "/oauth2/**").permitAll()
                .anyExchange().authenticated());
        http.oauth2Login(Customizer.withDefaults());
        http.oauth2Client(Customizer.withDefaults());
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.formLogin(Customizer.withDefaults());
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new DelegatingServerAuthenticationEntryPoint(new DelegatingServerAuthenticationEntryPoint.DelegateEntry(new PathPatternParserServerWebExchangeMatcher("/api/**"), new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))));
        return http.build();
    }

//    @Bean
//    public ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService() {
//        DefaultReactiveOAuth2UserService service = new DefaultReactiveOAuth2UserService();
//        service.setWebClient(getWebClient());
//
//        return service;
//    }
//
//    private WebClient getWebClient() {
//        Mono<ClientRegistration> bayun = clientRegistrationRepository.findByRegistrationId("bayun");
//        ClientRegistration clientRegistration = bayun.block();
//        String clientId = clientRegistration.getClientId();
//        String clientSecret = clientRegistration.getClientSecret();
//
//        return WebClient.builder()
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encode(clientId + ":" + clientSecret))
//                .build();
//    }

}
