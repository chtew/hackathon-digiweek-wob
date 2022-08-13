package de.digiweek.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Profile("!dev")
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${allowedorigins}")
    private String allowedorigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedorigins.split(","))
                .allowedHeaders("*")
                .allowCredentials(false);
    }

}
