package net.project.ems.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Optional: Find and register modules like Java 8 date/time modules if needed
        mapper.findAndRegisterModules();
        return mapper;
    }
}

