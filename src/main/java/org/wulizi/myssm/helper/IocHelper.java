package org.wulizi.myssm.helper;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.wulizi.myssm.annotations.Autowired;
import org.wulizi.myssm.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * ioc助手类
 *
 * @author wulizi
 */
public class IocHelper {
    public static void init(){}
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                //从map中获取bean和bean的实例
                Class<?> clazz = entry.getKey();
                Object instance = entry.getValue();
                //获取bean的所有属性
                Field[] fields = clazz.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Autowired.class)) {
                            Object beanFieldInstance = beanMap.get(field.getType());
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(instance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }

    }
}
