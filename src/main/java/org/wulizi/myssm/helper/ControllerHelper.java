package org.wulizi.myssm.helper;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.wulizi.myssm.annotations.RequestMapping;
import org.wulizi.myssm.entity.RequestMethod;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * controller助手类
 *
 * @author wulizi
 */
public class ControllerHelper {
    private final static Map<RequestMethod, Map<String, Method>> requestMap;

    private final static Map<String, Object> controllerMap;
    public static void init(){}

    static {
        requestMap = new EnumMap<>(RequestMethod.class);
        for (RequestMethod method : RequestMethod.values()) {
            requestMap.put(method, new HashMap<>());
        }
        controllerMap = new HashMap<>();
        doController();
    }

    private static void doController() {
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerSet)) {
            for (Class<?> controllerClass : controllerSet) {
                String baseUrl = "";
                if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping mapping = controllerClass.getAnnotation
                            (RequestMapping.class);
                    baseUrl = mapping.value();
                }
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                            String url = mapping.value();
                            if (StringUtils.isNotEmpty(url)) {
                                RequestMethod requestMethod = mapping.method();
                                //去除多余的 /
                                url = (baseUrl + "/" + url).replaceAll("/+", "/");
                                requestMap.get(requestMethod).put(url, method);
                                controllerMap.put(url, BeanHelper.getBean(controllerClass));
                            }
                        }
                    }
                }
            }
        }
    }

    public static Map<String, Object> getControllerMap() {
        return controllerMap;
    }

    public static Map<RequestMethod, Map<String, Method>> getRequestMap() {
        return requestMap;
    }
}
