// package com.example.sbchainssioicdoauth2.config.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
// import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
// import org.springframework.web.reactive.function.client.WebClient;

// @Configuration
// public class WebClientConfiguration {
    
//     @Bean
//     WebClient webClient(ClientRegistrationRepository clientRegRepo, OAuth2AuthorizedClientRepository oAuthClientRepo) {
//         ServletOAuth2AuthorizedClientExchangeFilterFunction filterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegRepo, oAuthClientRepo);

//         filterFunction.setDefaultOAuth2AuthorizedClient(true);
//         filterFunction.setDefaultClientRegistrationId("keycloak");
//             // .baseUrl("http://localhost:8081/resources")
//         return WebClient.builder().apply(filterFunction.oauth2Configuration()).build();
//     }
// }