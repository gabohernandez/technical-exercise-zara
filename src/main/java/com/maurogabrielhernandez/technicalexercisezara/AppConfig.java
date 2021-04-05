package com.maurogabrielhernandez.technicalexercisezara;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux
public class AppConfig implements WebFluxConfigurer {
	
	@Value("${mock.host}" )
	private String host;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET, POST, PUT, DELETE")
                .allowedHeaders("*");
    }
    
    @Bean
    public WebClient getWebClient() {
    	return WebClient.create(this.host);
    }
    
    
}
