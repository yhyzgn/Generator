package com.yhy.generator.utils;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 14:17
 * version: 1.0.0
 * desc   : 字符串工具
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static boolean isEmpty(String str) {
        return null == str || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean equals(String strA, String strB) {
        return null == strA && null == strB || null != strA && strA.equals(strB);
    }
}
