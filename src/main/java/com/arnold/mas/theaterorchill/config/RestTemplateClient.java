// RestTemplate bean config class.

package com.arnold.mas.theaterorchill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateClient {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
