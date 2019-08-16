package org.wulizi.myssm.http;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 伍立子
 */
public class InvokeEntity {
    private Object parentObj;
    private Method invokeMethod;
    private Object[] invokeArgs;
    private HttpServletResponse response;

    public InvokeEntity(Object parentObj, Method invokeMethod,
                        Object[] invokeArgs, HttpServletResponse response) {
        this.parentObj = parentObj;
        this.invokeMethod = invokeMethod;
        this.invokeArgs = invokeArgs;
        this.response = response;
    }

    public Object getParentObj() {
        return parentObj;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public Object[] getInvokeArgs() {
        return invokeArgs;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
