package org.wulizi.myssm.helper;


import org.wulizi.myssm.annotations.Controller;
import org.wulizi.myssm.annotations.Service;
import org.wulizi.myssm.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类操作助手
 * @author wulizi
 */
public class ClassHelper {
    /**
     * 定义所有类的集合
     */
    private static final Set<Class<?>> CLASS_SET;

    public static void init(){}
    static {
        String packageScan = ConfigHelper.getPackageScan();
        CLASS_SET = ClassUtil.getClassSet(packageScan);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有被@Service标注的类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取所有被@Controller标注的类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取被@Controller和被@Service标注的类
     */
    public static Set<Class<?>> getBeanClassSet() {
        Stream<Class<?>> combine = Stream.concat(getServiceClassSet().stream()
                ,getControllerClassSet().stream());
        return combine.collect(Collectors.toSet());
    }

    /**
     * 获取某个类的所有父类和子类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                classSet.add(clazz);
            }

        }
        return classSet;
    }

    /**
     * 获取带有某个注解的类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation>
                                                                annotation) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }
}
