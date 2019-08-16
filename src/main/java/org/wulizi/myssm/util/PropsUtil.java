package org.wulizi.myssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties工具类
 *
 * @author wulizi
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties properties = null;
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            LOGGER.error("load properties file is failure", e);
        }
        return properties;
    }

    /**
     * 获取字符串属性值(默认为空)
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }


    /**
     * 获取字符串属性值(可指定默认值)
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值属性值(默认为0)
     */
    public static int getInt(Properties props, String key) {

        return getInt(props, key, 0);
    }


    /**
     * 获取数值属性值(可指定默认值)
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取boolean属性值(默认为0)
     */
    public static boolean getBoolean(Properties props, String key) {

        return getBoolean(props, key, false);
    }


    /**
     * 获取boolean属性值(可指定默认值)
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
