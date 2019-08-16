package org.wulizi.myssm.annotations;


import org.wulizi.myssm.entity.RequestMethod;

import java.lang.annotation.*;

/**
 * @author 伍立子
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 表示映射的url
     *
     * @return .
     */
    String value() default "";

    /**
     * 表示提交方式
     *
     * @return .
     */
    RequestMethod method() default RequestMethod.ALL;
}
