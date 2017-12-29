package com.yhy.generator.core.java.type;

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
 * desc   :
 */
public class MethodSpec implements AbsSpec {
    private String name;
    private DocSpec docSpec;
    private List<AnnoSpec> annoSpecList;
    private Scope scope;
    private List<Modifier> modifierList;
    private Class<?> retType;
    private List<ParamSpec> paramSpecList;
    private boolean hasBody;

    public MethodSpec(String name) {
        this.name = name;
        annoSpecList = new ArrayList<>();
        modifierList = new ArrayList<>();
        paramSpecList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public MethodSpec setName(String name) {
        this.name = name;
        return this;
    }

    public DocSpec getDocSpec() {
        return docSpec;
    }

    public MethodSpec setDocSpec(DocSpec docSpec) {
        this.docSpec = docSpec;
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
        return this;
    }

    public MethodSpec addModifier(Modifier modifier) {
        modifierList.add(modifier);
        return this;
    }

    public Class<?> getRetType() {
        return retType;
    }

    public MethodSpec setRetType(Class<?> retType) {
        this.retType = retType;
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

    @Override
    public List<Class<?>> getClassList() {
        return null;
    }
}
