package com.yhy.generator.core.java;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:44
 * version: 1.0.0
 * desc   : 修饰符枚举
 */
public enum Modifier {
    STATIC("static"), FINAL("final"), ABSTRACT("abstract"), VOLATILE("volatile");

    private String modifier;

    Modifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifier() {
        return modifier;
    }
}
