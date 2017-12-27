package com.yhy.generator.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 11:19
 * version: 1.0.0
 * desc   :
 */
public class DateUtils {
    private DateUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static long getTime(Object date) {
        if (null == date) {
            return 0;
        }
        if (date instanceof Timestamp) {
            return getTime((Timestamp) date);
        } else if (date instanceof Date) {
            return getTime((Date) date);
        }
        return 0;
    }

    public static long getTime(Timestamp date) {
        return null != date ? date.getTime() : 0;
    }

    public static long getTime(Date date) {
        return null != date ? date.getTime() : 0;
    }
}
