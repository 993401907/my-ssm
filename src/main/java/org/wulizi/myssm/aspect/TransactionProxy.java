package org.wulizi.myssm.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wulizi.myssm.annotations.Transaction;
import org.wulizi.myssm.aspect.proxy.Proxy;
import org.wulizi.myssm.aspect.proxy.ProxyChain;
import org.wulizi.myssm.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * 事务代理
 * @author wulizi
 */
public class TransactionProxy implements Proxy {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
    private final static ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                LOGGER.debug("开始事务");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
            } catch (Throwable throwable) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.error("事务错误",throwable);
                throw new RuntimeException(throwable);
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            try {
                result = proxyChain.doProxyChain();
            } catch (Throwable throwable) {
                LOGGER.error("错误",throwable);
                throw new RuntimeException(throwable);
            }
        }
        return result;
    }


}
