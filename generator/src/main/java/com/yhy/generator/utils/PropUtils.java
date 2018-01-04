package com.yhy.generator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 13:36
 * version: 1.0.0
 * desc   : 属性工具
 */
public class PropUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtils.class);
    private static final Map<String, Properties> propMap = new HashMap<>();

    private PropUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static void load(String propName, String properties) {
        if (StringUtils.isEmpty(properties)) {
            return;
        }
        properties = properties.replaceAll("\\\\", "/");
        loadProperties(propName, properties);
    }

    public static void load(String properties) {
        load(null, properties);
    }

    public static long getLong(String key) {
        return getLong(key, -1);
    }

    public static long getLong(String key, long defValue) {
        String str = get(key);
        return StringUtils.isEmpty(str) ? defValue : Long.valueOf(str);
    }

    public static long getLong(String propName, String key, long defValue) {
        String str = get(propName, key);
        return StringUtils.isEmpty(str) ? defValue : Long.valueOf(str);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        String str = get(key);
        return StringUtils.isEmpty(str) ? defValue : Boolean.valueOf(str);
    }

    public static boolean getBoolean(String propName, String key, boolean defValue) {
        String str = get(propName, key);
        return StringUtils.isEmpty(str) ? defValue : Boolean.valueOf(str);
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int defValue) {
        String str = get(key);
        return StringUtils.isEmpty(str) ? defValue : Integer.valueOf(str);
    }

    public static int getInt(String propName, String key, int defValue) {
        String str = get(propName, key);
        return StringUtils.isEmpty(str) ? defValue : Integer.valueOf(str);
    }

    public static String get(String key) {
        Properties prop;
        for (Map.Entry<String, Properties> et : propMap.entrySet()) {
            prop = et.getValue();
            if (prop.containsKey(key)) {
                return prop.getProperty(key);
            }
        }
        return null;
    }

    public static String get(String propName, String key) {
        Properties prop = properties(propName);
        return null != prop ? prop.getProperty(key) : null;
    }

    public static String get(String propName, String key, String defValue) {
        String str = get(propName, key);
        return StringUtils.isEmpty(str) ? defValue : str;
    }

    public static Properties properties(String propName) {
        return propMap.get(propName);
    }

    private static void loadProperties(String propName, String properties) {
        if (StringUtils.isEmpty(properties)) {
            return;
        }

        properties = removeClasspath(properties);

        propName = StringUtils.isEmpty(propName) ? getPropertiesName(properties) : propName;

        if (StringUtils.isNotEmpty(properties) && StringUtils.isNotEmpty(propName)) {
            Properties prop = new Properties();
            try {
                prop.load(PropUtils.class.getResourceAsStream("/" + properties));
                propMap.put(propName, prop);
                LOGGER.info("Load properties '" + properties + "' as '" + propName + "' success.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getPropertiesName(String properties) {
        if (StringUtils.isEmpty(properties)) {
            return null;
        }
        String result = properties;
        if (properties.contains("/")) {
            int last = properties.lastIndexOf("/");
            result = properties.substring(last + 1);
        }
        if (properties.endsWith(".properties")) {
            result = result.substring(0, result.length() - 11);
        }

        return ConvertUtils.line2Hump(result);
    }

    private static String removeClasspath(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (path.startsWith("classpath:")) {
            path = path.substring(10);
        }
        return path;
    }
}
