package org.wulizi.myssm.annotations;

import java.lang.annotation.*;

/**
 * @author 伍立子
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     * 表示参数的别名，不填就和参数一致
     * @return .
     */
    String value() default "";
}
