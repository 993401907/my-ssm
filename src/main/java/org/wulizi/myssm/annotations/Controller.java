package org.wulizi.myssm.annotations;

import java.lang.annotation.*;

/**
 * @author 伍立子
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    /**
     * 表示给controller注册的别名
     * @return .
     */
    String value() default "";
}
