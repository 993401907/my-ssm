package org.wulizi.myssm.aspect.proxy;

/**
 * 代理接口
 * @author wulizi
 */
public interface Proxy {
    /**
     * 构建链式代理
     * @param proxyChain 链式代理
     * @return object
     */
    Object doProxy(ProxyChain proxyChain);
}
