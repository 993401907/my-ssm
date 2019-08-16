package org.wulizi.myssm;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.wulizi.myssm.annotations.RequestParam;
import org.wulizi.myssm.entity.RequestMethod;
import org.wulizi.myssm.helper.ControllerHelper;
import org.wulizi.myssm.helper.HelperLoader;
import org.wulizi.myssm.http.ControllerInvoke;
import org.wulizi.myssm.http.ControllerInvokeHandler;
import org.wulizi.myssm.http.InvokeEntity;
import org.wulizi.myssm.util.MapUtil;
import org.wulizi.myssm.util.ProxyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

/**
 * @author wulizi
 */
public class DispatchServlet extends HttpServlet {
    private Map<RequestMethod, Map<String, Method>> requestMapping;

    private Map<String, Object> controllerMap;

    @Override
    public void init() {
        HelperLoader.init();
        requestMapping = ControllerHelper.getRequestMap();
        controllerMap = ControllerHelper.getControllerMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp, RequestMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp, RequestMethod.POST);
    }

    private void doDispatch(HttpServletRequest req,
                            HttpServletResponse resp, RequestMethod requestMethod) throws IOException {
        //合并map
        Map<String, Method> handleMapping = MapUtil.mergeMap(
                requestMapping.get(RequestMethod.ALL), requestMapping.get(requestMethod));
        if (handleMapping.isEmpty()) {
            try {
                resp.getWriter().write("404 NOT FOUND");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        String url = getUrl(req);
        if (!handleMapping.containsKey(url)) {
            resp.getWriter().write("404 NOT FOUND");
            return;
        }

        Method method = handleMapping.get(url);
        Parameter[] parameters = method.getParameters();
        //新建一个装参数的数组
        Object[] paramValues = new Object[parameters.length];
        if (ArrayUtils.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];

                //先获取注解，如果没有注解，就使用字段的名称
                String key = StringUtils.isEmpty(getParamKey(parameter))
                        ? parameter.getName() : getParamKey(parameter);
                Class paramClass = parameter.getType();
                //给http两个类赋值
                if (paramClass == HttpServletResponse.class) {
                    paramValues[i] = resp;
                } else if (paramClass == HttpServletRequest.class) {
                    paramValues[i] = req;
                } else {
                    if (!StringUtils.isEmpty(req.getParameter(key))) {
                        String value = Arrays.toString(req.getParameterValues(key)).replaceAll("[\\[\\]]", "")
                                .replaceAll(",\\s", ",");
                        if (paramClass == String.class) {
                            paramValues[i] = value;
                        } else if (paramClass == int.class) {
                            paramValues[i] = Integer.valueOf(value);
                        }
                    }
                }

            }
        }

        //取出实例化的controller
        Object parentObj = controllerMap.get(url);

        // 将需要被代理的属性封装,动态代理这个方法
        InvokeEntity invokeEntity = new InvokeEntity(
                parentObj,
                method, paramValues, resp);
        ControllerInvokeHandler invokeHandler = ProxyUtil.getProxy(ControllerInvokeHandler.class,
                new ControllerInvoke(invokeEntity));
        invokeHandler.invoke();

    }


    /**
     * 从请求里面获取url
     *
     * @param req request请求
     * @return url
     */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "")
                .replaceAll("/+", "/");
        return url;
    }

    /**
     * 获取 MyRequestParam里面的值
     *
     * @param parameter 字段
     * @return .
     */
    private String getParamKey(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return parameter.getAnnotation(RequestParam.class).value();
        }
        return null;
    }

}
