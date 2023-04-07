package com.sankuai.avatar.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Bean 获取工具
 *
 * @author kui.xu
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    private static void setContext(ApplicationContext applicationContext) {
        if (ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }

    }

    public static <T> T getBean(String beanName, Class<T> beanType) {
        return ApplicationContextUtil.applicationContext.getBean(beanName, beanType);
    }

    public static <T> T getBean(Class<T> beanType) {
        return ApplicationContextUtil.applicationContext.getBean(beanType);
    }

    public static String getBeanName(Class<?> beanType) {
        String[] names = applicationContext.getBeanNamesForType(beanType);
        return names.length > 0 ? names[0] : null;
    }
}
