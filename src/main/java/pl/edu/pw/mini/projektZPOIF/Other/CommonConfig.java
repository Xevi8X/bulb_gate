package pl.edu.pw.mini.projektZPOIF.Other;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }
}
