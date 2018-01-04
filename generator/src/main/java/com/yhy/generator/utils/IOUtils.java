package com.yhy.generator.utils;

import java.io.Closeable;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 13:54
 * version: 1.0.0
 * desc   : IO工具
 */
public class IOUtils {
    private IOUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static void close(Closeable io) {
        try {
            io.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
