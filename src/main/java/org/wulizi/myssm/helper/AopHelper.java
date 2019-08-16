package org.wulizi.myssm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wulizi.myssm.annotations.Service;
import org.wulizi.myssm.aspect.annotation.Aspect;
import org.wulizi.myssm.aspect.proxy.AbstractAspectProxy;
import org.wulizi.myssm.aspect.proxy.Proxy;
import org.wulizi.myssm.aspect.proxy.ProxyManager;
import org.wulizi.myssm.aspect.TransactionProxy;
import org.wulizi.myssm.util.MapUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * aop帮助类
 *
 * @author wulizi
 */
public class AopHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    public static void init() {
    }

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop 错误", e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }


    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectMap(proxyMap);
        addTransactionMap(proxyMap);
        return proxyMap;
    }

    private static void addTransactionMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(
                Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    private static void addAspectMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper
                (AbstractAspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(
            Map<Class<?>, Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        int mapCount = MapUtil.getSetValueCount(proxyMap);
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>(mapCount);
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
