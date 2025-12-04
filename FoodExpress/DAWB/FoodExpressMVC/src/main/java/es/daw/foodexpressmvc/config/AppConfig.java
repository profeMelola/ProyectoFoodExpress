package es.daw.foodexpressmvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${api.api-url}")
    private String apiURL;

    @Value("${api.auth-url}")
    private String authURL;

    /*
    Estos métodos crean y configuran dos instancias distintas de WebClient, una para autenticación (webClientAuth) y otra para el API.
    WebClient es parte del módulo Spring WebFlux y se usa para hacer peticiones HTTP de forma reactiva y no bloqueante,
    aunque también se puede usar en aplicaciones no reactivas.
    Cada cliente está preconfigurado con una URL base diferente, lo cual hace que cuando uses webClient.get() o webClient.post() no necesites escribir la URL completa.
     */

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public WebClient webClientAPI(WebClient.Builder builder) {
        //return WebClient.builder().baseUrl(apiURL).build();
        return builder
                .baseUrl(apiURL)
                .build();
    }

    @Bean
    public WebClient webClientAuth(WebClient.Builder builder) {
        return builder
                .baseUrl(authURL)
                .build();
    }

}
