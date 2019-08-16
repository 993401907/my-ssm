package org.wulizi.myssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author wulizi
 */
public class ReflectionUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object parent, Method method, Object[] args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(parent, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量
     */
    public static void setField(Object parent, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(parent,value);
        } catch (Exception e) {
            LOGGER.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }
}
