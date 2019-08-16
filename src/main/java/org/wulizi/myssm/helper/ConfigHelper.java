package org.wulizi.myssm.helper;

import org.wulizi.myssm.config.ConfigConstant;
import org.wulizi.myssm.util.PropsUtil;

import java.util.Properties;

/**
 * @author wulizi
 */
public class ConfigHelper {
    private final static Properties CONFIG_PROPS =
            PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);


    /**
     * 获得jdbc驱动
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取jdbc的url
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取jdbc的username
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc的password
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取扫描的包名
     */
    public static String getPackageScan() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.PACKAGE_SCAN);
    }
}
