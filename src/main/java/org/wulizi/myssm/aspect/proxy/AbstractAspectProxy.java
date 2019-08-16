package org.wulizi.myssm.aspect.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 代理模版
 * @author wulizi
 */
public abstract class AbstractAspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAspectProxy.class);


    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Throwable throwable) {
            LOGGER.error("代理错误", throwable);
            error(cls, method, params, throwable);
            throw new RuntimeException(throwable);
        } finally {
            end();
        }

        return result;
    }

    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable throwable) {

    }

    public void end() {

    }

}
