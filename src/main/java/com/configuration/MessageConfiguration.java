package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfiguration {

    @Bean(name="messageSource")
    public ReloadableResourceBundleMessageSource bundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheMillis(500);
        messageSource.setBasenames(
            "classpath:messages_tr",
            "classpath:messages_en"
        );
        return messageSource;
    }
}
