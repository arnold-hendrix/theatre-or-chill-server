// provides access to property values in application.properties file.

package com.arnold.mas.theaterorchill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
@PropertySource("classpath:application.properties")
public class AppProperties {

    // inject Environment object used in accessing property value from application.properties file.
    @Autowired
    private Environment env;

    // method that returns the apiKey string value.
    public String getApiKey(String apiKey){
        return env.getProperty(apiKey);
    }
}
