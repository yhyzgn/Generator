package com.yhy.generator.core.java.type;

import com.yhy.generator.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 17:19
 * version: 1.0.0
 * desc   :
 */
public class ComplexType {
    private Class<?> type;
    private List<Class<?>> typeList;
    private List<ComplexType> childList;

    public ComplexType(Class<?> type) {
        this.type = type;
        typeList = new ArrayList<>();
        childList = new ArrayList<>();
    }

    public Class<?> getType() {
        return type;
    }

    public ComplexType setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public List<Class<?>> getTypeList() {
        return typeList;
    }

    public ComplexType setTypeList(List<Class<?>> typeList) {
        this.typeList = typeList;
        return this;
    }

    public ComplexType addType(Class<?> type) {
        typeList.add(type);
        return this;
    }

    public List<ComplexType> getChildList() {
        return childList;
    }

    public ComplexType setChildList(List<ComplexType> childList) {
        this.childList = childList;
        return this;
    }

    public ComplexType addChild(ComplexType complexType) {
        childList.add(complexType);
        return this;
    }

    public List<Class<?>> getClassList() {
        return getAllClass(this);
    }

    private List<Class<?>> getAllClass(ComplexType type) {
        List<Class<?>> result = new ArrayList<>();

        result.add(type.getType());
        if (null == childList || childList.isEmpty()) {
            if (null != type.getTypeList()) {
                result.addAll(type.getTypeList());
            }
        } else {
            for (ComplexType ct : type.getChildList()) {
                result.addAll(getAllClass(ct));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return getAllClassString(this);
    }

    private String getAllClassString(ComplexType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getType().getSimpleName()).append("<");
        if (null == type.getChildList() || type.getChildList().isEmpty()) {
            if (null != type.getTypeList()) {
                for (Class<?> clazz : type.getTypeList()) {
                    sb.append(clazz.getSimpleName()).append(", ");
                }
            }
            if (sb.toString().endsWith(", ")) {
//                sb.deleteCharAt(sb.length());
            }
        } else if (null != type.getChildList()) {
            for (ComplexType ct : type.getChildList()) {
                sb.append(getAllClassString(ct));
            }
        }
        sb.append(">");
        String result = sb.toString();
        if (StringUtils.equals("<>", result)) {
            result = "<?>";
        }
        return result;
    }
}
