package org.wulizi.myssm.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 基本类型转换类
 *
 * @author wulizi
 */
public final class CastUtil {

    /**
     * 转换为字符串(默认值为空)
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转换为字符串(可设置默认值)
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转换为double型(默认值为0)
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转换为double型(可设置默认值)
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isEmpty(strValue)) {
                try {
                    value = Double.parseDouble(strValue);

                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为long型(默认值为0)
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转换为long型(可设置默认值)
     */
    public static long castLong(Object obj, long defaultValue) {
        long value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isEmpty(strValue)) {
                try {
                    value = Long.parseLong(strValue);

                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }


    /**
     * 转换为int型(默认值为0)
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转换为int型(可设置默认值)
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isEmpty(strValue)) {
                try {
                    value = Integer.parseInt(strValue);

                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为boolean型(默认值false)
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转换为boolean型(可设默认值)
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        if (obj != null) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
