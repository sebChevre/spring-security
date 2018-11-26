package ch.devsecops.security.authentificationservice.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
public class AuthentificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentificationServiceApplication.class);
    }



    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }
}
