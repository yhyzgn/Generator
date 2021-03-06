package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 17:04
 * version: 1.0.0
 * desc   : 参数类型
 */
public class ParamSpec implements AbsSpec {
    // 参数名称
    private String name;
    // 具体类型
    private Clazz type;
    // 泛型
    private ComplexSpec complexSpec;
    // 注解
    private AnnoSpec annoSpec;

    public ParamSpec(String name, Clazz type) {
        this.name = name;
        this.type = type;
    }

    public ParamSpec(String name, ComplexSpec complexSpec) {
        this.name = name;
        this.complexSpec = complexSpec;
    }

    public Clazz getType() {
        return type;
    }

    public ParamSpec setType(Clazz type) {
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

    public List<Clazz> getClassList() {
        List<Clazz> result = new ArrayList<>();
        if (null != annoSpec) {
            result.add(annoSpec.getType());
        }
        if (null != type) {
            result.add(type);
        } else if (null != complexSpec && null != complexSpec.getClassList()) {
            result.addAll(complexSpec.getClassList());
        }
        return result;
    }

    @Override
    public String string(String indent) {
        StringBuilder sb = new StringBuilder();
        if (null != annoSpec) {
            sb.append(annoSpec.string("")).append(" ");
        }
        if (null != type) {
            sb.append(type.getSimpleName());
        } else if (null != complexSpec) {
            sb.append(complexSpec.string(""));
        }
        sb.append(" ").append(name);
        return sb.toString();
    }
}
