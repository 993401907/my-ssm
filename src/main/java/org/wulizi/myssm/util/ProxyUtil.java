package org.wulizi.myssm.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理工具类
 *
 * @author wulizi
 */
public class ProxyUtil {
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> clazz, InvocationHandler entity) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                entity);
    }
}
