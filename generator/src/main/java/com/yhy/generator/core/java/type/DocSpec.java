package com.yhy.generator.core.java.type;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:31
 * version: 1.0.0
 * desc   :
 */
public class DocSpec {
    private String type;
    private String value;

    public DocSpec(String value) {
        this.value = value;
    }

    public DocSpec(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}