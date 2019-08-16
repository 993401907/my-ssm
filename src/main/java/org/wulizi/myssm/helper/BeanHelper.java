package org.wulizi.myssm.helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wulizi.myssm.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bean助手类
 * @author wulizi
 */
public class BeanHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    public static void init(){}
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();

        for (Class<?> clazz : beanClassSet) {
            BEAN_MAP.put(clazz,ReflectionUtil.newInstance(clazz));
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("没有这个bean");
        }
        return (T) BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> cls, Object object) {
        BEAN_MAP.put(cls,object);
    }


}
