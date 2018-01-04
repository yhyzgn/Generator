package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:31
 * version: 1.0.0
 * desc   : 文档注释类型
 */
public class DocSpec implements AbsSpec {
    // 注释类型
    private String type;
    // 注释值
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

    @Override
    public List<Clazz> getClassList() {
        return null;
    }

    @Override
    public String string(String indent) {
        return null;
    }
}
