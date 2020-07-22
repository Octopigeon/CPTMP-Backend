package io.github.octopigeon.cptmpservice.config;

import org.springframework.context.ApplicationContext;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/22
 * @last-check-in Gh Li
 * @date 2020/7/22
 */
public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return  applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String name, Class<T> tClass){
        return applicationContext.getBean(name, tClass);
    }

    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
}
