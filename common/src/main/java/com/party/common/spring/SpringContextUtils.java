package com.party.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


/**
 * @author wei.li
 * @version 2016/8/1
 */
public class SpringContextUtils {

    public static Object getBean(String beanName) throws BeansException {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return wac.getBean(beanName);
    }
}
