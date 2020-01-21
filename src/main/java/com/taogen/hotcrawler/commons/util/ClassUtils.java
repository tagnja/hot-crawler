package com.taogen.hotcrawler.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtils {
    private static Logger log = LoggerFactory.getLogger(ClassUtils.class);

    public static Class getClassByClassPath(String classPath){
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
