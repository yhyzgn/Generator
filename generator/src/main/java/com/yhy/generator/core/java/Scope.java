package com.yhy.generator.core.java;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:40
 * version: 1.0.0
 * desc   : 作用域枚举
 */
public enum Scope {
    DEFAULT(""), PUBLIC("public"), PROTECTED("protected"), PRIVATE("private");

    private String scope;

    Scope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }
}
