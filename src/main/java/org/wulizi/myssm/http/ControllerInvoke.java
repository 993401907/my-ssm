package org.wulizi.myssm.http;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * 控制器代理，代理获取返回值
 * @author 伍立子
 */
public class ControllerInvoke implements InvocationHandler {
    private Object parentObj;
    private Method invokeMethod;
    private Object[] invokeArgs;
    private HttpServletResponse response;

    public ControllerInvoke(InvokeEntity invokeEntity) {
        this.parentObj = invokeEntity.getParentObj();
        this.invokeMethod = invokeEntity.getInvokeMethod();
        this.invokeArgs = invokeEntity.getInvokeArgs();
        this.response = invokeEntity.getResponse();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = invokeMethod.invoke(parentObj, invokeArgs);
        if (result != null) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            String json = JSONObject.toJSONString(result);
            writer.write(json);
        }
        return null;
    }
}
