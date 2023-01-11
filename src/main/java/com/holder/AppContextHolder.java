package com.holder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        AppContextHolder.applicationContext = applicationContext;
    }
    public static <T> T getBean(Class<T> beanName) {
        return applicationContext.getBean(beanName);
    }

}
