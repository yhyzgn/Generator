package com.yhy.generator.core.xml;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 16:20
 * version: 1.0.0
 * desc   :
 */
public class Attribute {
    private String name;
    private String value;

    public Attribute(String name) {
        this.name = name;
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
