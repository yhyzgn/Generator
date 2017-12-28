package com.yhy.generator.core.java.type;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 17:04
 * version: 1.0.0
 * desc   :
 */
public class ParamSpec {
    private Class<?> type;
    private String name;

    public ParamSpec(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public ParamSpec setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public ParamSpec setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return type.getSimpleName() + " " + name;
    }
}
