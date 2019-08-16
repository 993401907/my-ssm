package org.wulizi.myssm.aspect.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * @author wulizi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
