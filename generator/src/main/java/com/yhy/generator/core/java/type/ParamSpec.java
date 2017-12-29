package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 17:04
 * version: 1.0.0
 * desc   :
 */
public class ParamSpec implements AbsSpec {
    private String name;
    private Class<?> type;
    private ComplexSpec complexSpec;
    private AnnoSpec annoSpec;

    public ParamSpec(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public ParamSpec(String name, ComplexSpec complexSpec) {
        this.name = name;
        this.complexSpec = complexSpec;
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

    public ComplexSpec getComplexSpec() {
        return complexSpec;
    }

    public AnnoSpec getAnnoSpec() {
        return annoSpec;
    }

    public ParamSpec setAnnoSpec(AnnoSpec annoSpec) {
        this.annoSpec = annoSpec;
        return this;
    }

    public ParamSpec setComplexSpec(ComplexSpec complexSpec) {
        this.complexSpec = complexSpec;
        return this;
    }

    public List<Class<?>> getClassList() {
        List<Class<?>> result = new ArrayList<>();
        if (null != type) {
            result.add(type);
        }
        if (null != complexSpec && null != complexSpec.getClassList()) {
            result.addAll(complexSpec.getClassList());
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (null != annoSpec) {
            sb.append(annoSpec.toString()).append(" ");
        }
        if (null != type) {
            sb.append(type.getName());
        } else if (null != complexSpec) {
            sb.append(complexSpec.toString());
        }
        sb.append(" ").append(name);
        return sb.toString();
    }
}
