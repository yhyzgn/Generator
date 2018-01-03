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
    private List<DocSpec> docSpecList;
    private List<AnnoSpec> annoSpecList;
    private Scope scope;
    private List<Modifier> modifierList;
    private Class<?> retType;
    private ComplexSpec retComplex;
    private List<ParamSpec> paramSpecList;
    private boolean hasBody;
    private List<StMentSpec> stMentSpecList;
    private List<Class<?>> exceptionList;

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

    public Class<?> getRetType() {
        return retType;
    }

    public MethodSpec setRetType(Class<?> retType) {
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

    public List<Class<?>> getExceptionList() {
        return exceptionList;
    }

    public MethodSpec setExceptionList(List<Class<?>> exceptionList) {
        this.exceptionList = exceptionList;
        return this;
    }

    public MethodSpec addException(Class<?> exception) {
        exceptionList.add(exception);
        return this;
    }

    private void check() {
        hasBody = null == modifierList || !modifierList.contains(Modifier.ABSTRACT);
    }

    @Override
    public List<Class<?>> getClassList() {
        List<Class<?>> result = new ArrayList<>();
        if (null != annoSpecList && !annoSpecList.isEmpty()) {
            for (AnnoSpec annoSpec : annoSpecList) {
                result.addAll(annoSpec.getClassList());
            }
        }
        if (null != retType && retType != Void.class) {
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

        if (null != docSpecList && !docSpecList.isEmpty()) {
            sb.append(indent).append("/**").append(lineSeparator);
            for (DocSpec doc : docSpecList) {
                sb.append(indent).append(" * " + doc.getType() + " " + doc.getValue()).append(lineSeparator);
            }
            sb.append(indent).append(" */").append(lineSeparator);
        }

        sb.append(indent);
        if (null != scope) {
            sb.append(scope.getScope()).append(" ");
        }
        if (null != modifierList && !modifierList.isEmpty()) {
            for (Modifier modifier : modifierList) {
                sb.append(modifier.getModifier()).append(" ");
            }
        }
        if (null != retType && retType != Void.class) {
            sb.append(retType.getSimpleName());
        } else if (null != retComplex) {
            sb.append(retComplex.string(indent));
        } else {
            sb.append("void");
        }
        sb.append(" ").append(name).append("(");

        if (null != paramSpecList && !paramSpecList.isEmpty()) {
            for (int i = 0; i < paramSpecList.size(); i++) {
                sb.append(paramSpecList.get(i).string(indent));
                if (i < paramSpecList.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(")");

        if (null != exceptionList && !exceptionList.isEmpty()) {
            sb.append(" throws ");
            for (int i = 0; i < exceptionList.size(); i++) {
                sb.append(exceptionList.get(i).getSimpleName());
                if (i < exceptionList.size() - 1) {
                    sb.append(", ");
                }
            }
        }

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
