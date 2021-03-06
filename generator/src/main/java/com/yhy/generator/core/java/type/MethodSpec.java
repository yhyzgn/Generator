package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.Modifier;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.abs.AbsSpec;
import com.yhy.generator.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:29
 * version: 1.0.0
 * desc   : 方法类型
 */
public class MethodSpec implements AbsSpec {
    // 方法名称
    private String name;
    // 注释列表
    private List<DocSpec> docSpecList;
    // 注解列表
    private List<AnnoSpec> annoSpecList;
    // 作用域
    private Scope scope;
    // 修饰符列表
    private List<Modifier> modifierList;
    // 返回值类型
    private Clazz retType;
    // 返回值泛型
    private ComplexSpec retComplex;
    // 参数列表
    private List<ParamSpec> paramSpecList;
    // 是否有方法体
    private boolean hasBody;
    // 表达式列表
    private List<StMentSpec> stMentSpecList;
    // 异常列表
    private List<Clazz> exceptionList;

    public MethodSpec(String name) {
        this.name = name;
        docSpecList = new ArrayList<>();
        annoSpecList = new ArrayList<>();
        modifierList = new ArrayList<>();
        paramSpecList = new ArrayList<>();
        stMentSpecList = new ArrayList<>();
        exceptionList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public MethodSpec setName(String name) {
        this.name = name;
        return this;
    }

    public List<DocSpec> getDocSpecList() {
        return docSpecList;
    }

    public MethodSpec setDocSpecList(List<DocSpec> docSpecList) {
        this.docSpecList = docSpecList;
        return this;
    }

    public MethodSpec addDocSpec(DocSpec docSpec) {
        docSpecList.add(docSpec);
        return this;
    }

    public List<AnnoSpec> getAnnoSpecList() {
        return annoSpecList;
    }

    public MethodSpec setAnnoSpecList(List<AnnoSpec> annoSpecList) {
        this.annoSpecList = annoSpecList;
        return this;
    }

    public MethodSpec addAnnoSpec(AnnoSpec annoSpec) {
        annoSpecList.add(annoSpec);
        return this;
    }

    public Scope getScope() {
        return scope;
    }

    public MethodSpec setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public List<Modifier> getModifierList() {
        return modifierList;
    }

    public MethodSpec setModifierList(List<Modifier> modifierList) {
        this.modifierList = modifierList;
        check();
        return this;
    }

    public MethodSpec addModifier(Modifier modifier) {
        modifierList.add(modifier);
        check();
        return this;
    }

    public Clazz getRetType() {
        return retType;
    }

    public MethodSpec setRetType(Clazz retType) {
        this.retType = retType;
        return this;
    }

    public ComplexSpec getRetComplex() {
        return retComplex;
    }

    public MethodSpec setRetComplex(ComplexSpec retComplex) {
        this.retComplex = retComplex;
        return this;
    }

    public List<ParamSpec> getParamSpecList() {
        return paramSpecList;
    }

    public MethodSpec setParamSpecList(List<ParamSpec> paramSpecList) {
        this.paramSpecList = paramSpecList;
        return this;
    }

    public MethodSpec addParamSpec(ParamSpec paramSpec) {
        paramSpecList.add(paramSpec);
        return this;
    }

    public boolean getHasBody() {
        return hasBody;
    }

    public MethodSpec setHasBody(boolean hasBody) {
        this.hasBody = hasBody;
        return this;
    }

    public List<StMentSpec> getStMentSpecList() {
        return stMentSpecList;
    }

    public MethodSpec setStMentSpecList(List<StMentSpec> stMentSpecList) {
        this.stMentSpecList = stMentSpecList;
        check();
        return this;
    }

    public MethodSpec addStMentSpec(StMentSpec stMentSpec) {
        stMentSpecList.add(stMentSpec);
        check();
        return this;
    }

    public List<Clazz> getExceptionList() {
        return exceptionList;
    }

    public MethodSpec setExceptionList(List<Clazz> exceptionList) {
        this.exceptionList = exceptionList;
        return this;
    }

    public MethodSpec addException(Clazz exception) {
        exceptionList.add(exception);
        return this;
    }

    private void check() {
        hasBody = null == modifierList || !modifierList.contains(Modifier.ABSTRACT);
    }

    @Override
    public List<Clazz> getClassList() {
        List<Clazz> result = new ArrayList<>();
        if (null != annoSpecList && !annoSpecList.isEmpty()) {
            for (AnnoSpec annoSpec : annoSpecList) {
                result.addAll(annoSpec.getClassList());
            }
        }
        if (null != retType && !StringUtils.equals("Void", retType.getSimpleName())) {
            result.add(retType);
        } else if (null != retComplex) {
            result.addAll(retComplex.getClassList());
        }
        if (null != paramSpecList && !paramSpecList.isEmpty()) {
            for (ParamSpec paramSpec : paramSpecList) {
                result.addAll(paramSpec.getClassList());
            }
        }
        if (hasBody && null != stMentSpecList && !stMentSpecList.isEmpty()) {
            for (StMentSpec stMentSpec : stMentSpecList) {
                result.addAll(stMentSpec.getClassList());
            }
        }
        if (null != exceptionList && !exceptionList.isEmpty()) {
            result.addAll(exceptionList);
        }
        return result;
    }

    @Override
    public String string(String indent) {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        // 添加注释
        if (null != docSpecList && !docSpecList.isEmpty()) {
            sb.append(indent).append("/**").append(lineSeparator);
            for (DocSpec doc : docSpecList) {
                sb.append(indent).append(" * " + doc.getType() + " " + doc.getValue()).append(lineSeparator);
            }
            sb.append(indent).append(" */").append(lineSeparator);
        }

        // 添加方法名
        sb.append(indent);
        if (null != scope) {
            sb.append(scope.getScope()).append(" ");
        }
        if (null != modifierList && !modifierList.isEmpty()) {
            for (Modifier modifier : modifierList) {
                sb.append(modifier.getModifier()).append(" ");
            }
        }
        if (null != retType && !StringUtils.equals("Void", retType.getSimpleName())) {
            sb.append(retType.getSimpleName());
        } else if (null != retComplex) {
            sb.append(retComplex.string(indent));
        } else {
            sb.append("void");
        }
        sb.append(" ").append(name).append("(");

        // 添加参数
        if (null != paramSpecList && !paramSpecList.isEmpty()) {
            for (int i = 0; i < paramSpecList.size(); i++) {
                sb.append(paramSpecList.get(i).string(indent));
                if (i < paramSpecList.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(")");

        // 添加异常
        if (null != exceptionList && !exceptionList.isEmpty()) {
            sb.append(" throws ");
            for (int i = 0; i < exceptionList.size(); i++) {
                sb.append(exceptionList.get(i).getSimpleName());
                if (i < exceptionList.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        // 判断是否有方法体，如果有便添加
        if (!hasBody) {
            sb.append(";");
        } else {
            sb.append(" {").append(lineSeparator);
            if (null != stMentSpecList && !stMentSpecList.isEmpty()) {
                for (StMentSpec stMentSpec : stMentSpecList) {
                    sb.append(stMentSpec.string(indent + "\t"));
                }
            }
            sb.append(indent).append("}");
        }

        return sb.toString();
    }
}
