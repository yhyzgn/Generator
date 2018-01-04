package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.Modifier;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:29
 * version: 1.0.0
 * desc   : 字段类型
 */
public class FieldSpec implements AbsSpec {
    // 字段名称
    private String name;
    // 注释
    private DocSpec docSpec;
    // 注解列表
    private List<AnnoSpec> annoSpecList;
    // 作用域
    private Scope scope;
    // 修饰符列表
    private List<Modifier> modifierList;
    // 普通类型
    private Clazz type;
    // 泛型
    private ComplexSpec complexSpec;

    public FieldSpec(String name) {
        this.name = name;
        annoSpecList = new ArrayList<>();
        modifierList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public FieldSpec setName(String name) {
        this.name = name;
        return this;
    }

    public DocSpec getDocSpec() {
        return docSpec;
    }

    public FieldSpec setDocSpec(DocSpec docSpec) {
        this.docSpec = docSpec;
        return this;
    }

    public List<AnnoSpec> getAnnoSpecList() {
        return annoSpecList;
    }

    public FieldSpec setAnnoSpecList(List<AnnoSpec> annoSpecList) {
        this.annoSpecList = annoSpecList;
        return this;
    }

    public FieldSpec addAnnoSpec(AnnoSpec annoSpec) {
        annoSpecList.add(annoSpec);
        return this;
    }

    public Scope getScope() {
        return scope;
    }

    public FieldSpec setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public List<Modifier> getModifierList() {
        return modifierList;
    }

    public FieldSpec setModifierList(List<Modifier> modifierList) {
        this.modifierList = modifierList;
        return this;
    }

    public FieldSpec addModifier(Modifier modifier) {
        modifierList.add(modifier);
        return this;
    }

    public Clazz getType() {
        return type;
    }

    public FieldSpec setType(Clazz type) {
        this.type = type;
        return this;
    }

    public ComplexSpec getComplexSpec() {
        return complexSpec;
    }

    public FieldSpec setComplexSpec(ComplexSpec complexSpec) {
        this.complexSpec = complexSpec;
        return this;
    }

    public List<Clazz> getClassList() {
        List<Clazz> result = new ArrayList<>();
        if (null != annoSpecList) {
            for (AnnoSpec anno : annoSpecList) {
                if (null != anno.getClassList()) {
                    result.addAll(anno.getClassList());
                }
            }
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
        String lineSeparator = System.getProperty("line.separator");
        if (null != docSpec) {
            sb.append(indent).append("// ").append(docSpec.getValue()).append(lineSeparator);
        }
        if (null != annoSpecList && !annoSpecList.isEmpty()) {
            for (AnnoSpec anno : annoSpecList) {
                sb.append(anno.string(indent)).append(lineSeparator);
            }
        }
        sb.append(indent);
        sb.append(scope.getScope()).append(" ");
        for (Modifier modifier : modifierList) {
            sb.append(modifier.getModifier()).append(" ");
        }

        if (null != type) {
            sb.append(type.getSimpleName());
        } else if (null != complexSpec) {
            sb.append(complexSpec.string(indent));
        }
        sb.append(" ").append(name).append(";").append(lineSeparator);
        return sb.toString();
    }
}
