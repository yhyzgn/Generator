package com.yhy.generator.core.java.type;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 15:04
 * version: 1.0.0
 * desc   :
 */
public class AnnoSpec {
    private Class<?> type;

    public AnnoSpec(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "@" + type.getSimpleName() + System.getProperty("line.separator");
    }
}
