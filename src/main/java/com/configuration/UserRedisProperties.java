package com.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "user-detail")
public class UserRedisProperties {

    private int userCacheMinute;
    private int listAnnualCacheMinute;
    private int totalAnnualCacheMinute;
}
