package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.Modifier;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.*;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:28
 * version: 1.0.0
 * desc   :
 */
public class TypeSpec implements AbsSpec {
    private String name;
    private boolean isInter;
    private List<DocSpec> docSpecList;
    private List<AnnoSpec> annoSpecList;
    private Scope scope;
    private List<Modifier> modifierList;
    private Clazz extClass;
    private List<Clazz> interList;
    private List<FieldSpec> fieldSpecList;
    private List<MethodSpec> methodSpecList;

    public TypeSpec(String name) {
        this(name, false);
    }

    public TypeSpec(String name, boolean isInter) {
        this.name = name;
        this.isInter = isInter;
        docSpecList = new ArrayList<>();
        annoSpecList = new ArrayList<>();
        modifierList = new ArrayList<>();
        interList = new ArrayList<>();
        fieldSpecList = new ArrayList<>();
        methodSpecList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public TypeSpec setName(String name) {
        this.name = name;
        return this;
    }

    public boolean getInter() {
        return isInter;
    }

    public TypeSpec setInter(boolean inter) {
        isInter = inter;
        return this;
    }

    public List<DocSpec> getDocSpecList() {
        return docSpecList;
    }

    public TypeSpec setDocSpecList(List<DocSpec> docSpecList) {
        this.docSpecList = docSpecList;
        return this;
    }

    public TypeSpec addDocSpec(DocSpec docSpec) {
        docSpecList.add(docSpec);
        return this;
    }

    public List<AnnoSpec> getAnnoSpecList() {
        return annoSpecList;
    }

    public TypeSpec setAnnoSpecList(List<AnnoSpec> annoSpecList) {
        this.annoSpecList = annoSpecList;
        return this;
    }

    public TypeSpec addAnnoSpec(AnnoSpec annoSpec) {
        annoSpecList.add(annoSpec);
        return this;
    }

    public Scope getScope() {
        return scope;
    }

    public TypeSpec setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public List<Modifier> getModifierList() {
        return modifierList;
    }

    public TypeSpec setModifierList(List<Modifier> modifierList) {
        this.modifierList = modifierList;
        return this;
    }

    public TypeSpec addModifier(Modifier modifier) {
        modifierList.add(modifier);
        return this;
    }

    public Clazz getExtClass() {
        return extClass;
    }

    public TypeSpec setExtClass(Clazz extClass) {
        this.extClass = extClass;
        return this;
    }

    public List<Clazz> getInterList() {
        return interList;
    }

    public TypeSpec setInterList(List<Clazz> interList) {
        this.interList = interList;
        return this;
    }

    public TypeSpec addInter(Clazz inter) {
        interList.add(inter);
        return this;
    }

    public List<FieldSpec> getFieldSpecList() {
        return fieldSpecList;
    }

    public TypeSpec setFieldSpecList(List<FieldSpec> fieldSpecList) {
        this.fieldSpecList = fieldSpecList;
        return this;
    }

    public TypeSpec addFieldSpec(FieldSpec fieldSpec) {
        fieldSpecList.add(fieldSpec);
        return this;
    }

    public List<MethodSpec> getMethodSpecList() {
        return methodSpecList;
    }

    public TypeSpec setMethodSpecList(List<MethodSpec> methodSpecList) {
        this.methodSpecList = methodSpecList;
        return this;
    }

    public TypeSpec addMethodSpec(MethodSpec methodSpec) {
        methodSpecList.add(methodSpec);
        return this;
    }

    public List<Clazz> getClassList() {
        List<Clazz> temp = new ArrayList<>();
        if (null != annoSpecList) {
            for (AnnoSpec anno : annoSpecList) {
                if (null != anno.getClassList()) {
                    temp.addAll(anno.getClassList());
                }
            }
        }
        if (null != extClass) {
            temp.add(extClass);
        }
        if (null != interList) {
            temp.addAll(interList);
        }
        if (null != fieldSpecList) {
            for (FieldSpec field : fieldSpecList) {
                if (null != field.getClassList()) {
                    temp.addAll(field.getClassList());
                }
            }
        }
        if (null != methodSpecList) {
            for (MethodSpec method : methodSpecList) {
                if (null != method.getClassList()) {
                    temp.addAll(method.getClassList());
                }
            }
        }

        // 去重并排序
        Set<Clazz> result = null;
        if (!temp.isEmpty()) {
            result = new TreeSet<>(new Comparator<Clazz>() {
                @Override
                public int compare(Clazz o1, Clazz o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (Clazz clazz : temp) {
                if (!result.contains(clazz)) {
                    result.add(clazz);
                }
            }
        }
        return new ArrayList<>(result);
    }

    @Override
    public String string(String indent) {
        return getString("");
    }

    public String getString(String indent) {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        if (null != docSpecList && !docSpecList.isEmpty()) {
            sb.append("/**").append(lineSeparator);
            for (DocSpec doc : docSpecList) {
                sb.append(" * " + doc.getType() + " " + doc.getValue()).append(lineSeparator);
            }
            sb.append(" */").append(lineSeparator);
        }
        if (null != annoSpecList && !annoSpecList.isEmpty()) {
            for (AnnoSpec anno : annoSpecList) {
                sb.append(anno.string(indent)).append(lineSeparator);
            }
        }
        sb.append(scope.getScope()).append(" ");
        if (null != modifierList && !modifierList.isEmpty()) {
            for (Modifier modifier : modifierList) {
                sb.append(modifier.getModifier()).append(" ");
            }
        }
        if (isInter) {
            sb.append("interface ");
        } else {
            sb.append("class ");
        }
        sb.append(name).append(" ");
        if (null != extClass) {
            sb.append("extends ").append(extClass.getSimpleName()).append(" ");
        }
        if (null != interList && !interList.isEmpty()) {
            sb.append("implements ");
            for (int i = 0; i < interList.size(); i++) {
                sb.append(interList.get(i).getSimpleName());
                if (i < interList.size() - 1) {
                    sb.append(",");
                }
                sb.append(" ");
            }
        }
        sb.append("{").append(lineSeparator);

        if (null != fieldSpecList && !fieldSpecList.isEmpty()) {
            for (FieldSpec field : fieldSpecList) {
                sb.append(field.string(indent + "\t"));
            }
            sb.append(lineSeparator);
        }

        if (null != methodSpecList && !methodSpecList.isEmpty()) {
            for (int i = 0; i < methodSpecList.size(); i++) {
                sb.append(methodSpecList.get(i).string(indent + "\t")).append(lineSeparator);
                if (i < methodSpecList.size() - 1) {
                    sb.append(lineSeparator);
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
