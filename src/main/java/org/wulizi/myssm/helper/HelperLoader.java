package org.wulizi.myssm.helper;

/**
 * 加载相应的helper
 *
 * @author wulizi
 */
public final class HelperLoader {
    public static void init() {
//        Class<?>[] classList = {
//                ClassHelper.class,
//                BeanHelper.class,
//                AopHelper.class,
//                IocHelper.class,
//                ControllerHelper.class
//        };
//        for (Class<?> clazz : classList) {
//            ClassUtil.loadClass(clazz.getName(),true);
//        }
        ClassHelper.init();
        BeanHelper.init();
        AopHelper.init();
        IocHelper.init();
        ControllerHelper.init();
    }
}
