/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.baojie.jeesite.util.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：将ApplicationContext传入，直接操作bean
 */
public class SpringContextHolder {

    private static ApplicationContext context;

    /**
     * 在ApplicationContext中获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    public static boolean existBean(String beanName) {
        return getApplicationContext().containsBean(beanName);
    }

    /**
     * 校验Bean是否存在方法
     * @param beanName
     * @param methodName
     * @param parameterTypes
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static boolean existBeanAndMethod(String beanName, String methodName, Class<?>[] parameterTypes){
        if(! getApplicationContext().containsBean(beanName)) {
            return false;
        }

        Object serviceImpl = SpringContextHolder.getBean(beanName);
        Method method;
        try {
            method = serviceImpl.getClass().getMethod(methodName,parameterTypes);
            if (method == null) {
                return false;
            }
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        Validate.validState(context != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
        return context;
    }
}