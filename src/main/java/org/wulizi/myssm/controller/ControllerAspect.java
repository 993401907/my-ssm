package org.wulizi.myssm.controller;

import org.wulizi.myssm.annotations.Controller;
import org.wulizi.myssm.aspect.annotation.Aspect;
import org.wulizi.myssm.aspect.proxy.AbstractAspectProxy;

import java.lang.reflect.Method;

/**
 * 控制器切面，测试
 * @author wulizi
 */
@Aspect(Controller.class)
public class ControllerAspect extends AbstractAspectProxy {
    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println(123);
    }
}
