package com.lianxiao.demo.simpleserver.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//@Component
//public class SpringUtils implements ApplicationContextAware {
//    private static ApplicationContext applicationContext;
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        SpringUtils.applicationContext = applicationContext;
//    }
//
//    public ApplicationContext getApplicationContext(){
//        return applicationContext;
//    }
//
//    public static Object getBean(String beanName){
//        return applicationContext.getBean(beanName);
//    }
//
//    public static <T> T getBean(Class<T> clazz){
//        return (T)applicationContext.getBean(clazz);
//    }
//}
