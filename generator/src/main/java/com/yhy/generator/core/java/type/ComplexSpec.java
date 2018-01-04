package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 17:19
 * version: 1.0.0
 * desc   : 复杂类型，即泛型
 */
public class ComplexSpec implements AbsSpec {
    // 外层类型，如：Map
    private Clazz type;
    // 内部类型列表，如：Map<String, Object>
    private List<Clazz> typeList;
    // 内部泛型列表，如：Map<String, List<Object>>
    private List<ComplexSpec> childList;

    public ComplexSpec(Clazz type) {
        this.type = type;
        typeList = new ArrayList<>();
        childList = new ArrayList<>();
    }

    public Clazz getType() {
        return type;
    }

    public ComplexSpec setType(Clazz type) {
        this.type = type;
        return this;
    }

    public List<Clazz> getTypeList() {
        return typeList;
    }

    public ComplexSpec setTypeList(List<Clazz> typeList) {
        this.typeList = typeList;
        return this;
    }

    public ComplexSpec addType(Clazz type) {
        typeList.add(type);
        return this;
    }

    public List<ComplexSpec> getChildList() {
        return childList;
    }

    public ComplexSpec setChildList(List<ComplexSpec> childList) {
        this.childList = childList;
        return this;
    }

    public ComplexSpec addChild(ComplexSpec complexSpec) {
        childList.add(complexSpec);
        return this;
    }

    public List<Clazz> getClassList() {
        return getAllClass(this);
    }

    @Override
    public String string(String indent) {
        return getString(this);
    }

    /**
     * 递归获取所有用到的类
     *
     * @param type 当前泛型节点
     * @return 所有用到的类
     */
    private List<Clazz> getAllClass(ComplexSpec type) {
        List<Clazz> result = new ArrayList<>();
        if (null != type.getType()) {
            result.add(type.getType());
        }
        if (null == childList || childList.isEmpty()) {
            if (null != type.getTypeList()) {
                result.addAll(type.getTypeList());
            }
        } else {
            for (ComplexSpec ct : type.getChildList()) {
                result.addAll(getAllClass(ct));
            }
        }

        return result;
    }

    /**
     * 递归将当前泛型节点序列化成字符串
     *
     * @param type 当前节点
     * @return 序列化后的字符串
     */
    private String getString(ComplexSpec type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getType().getSimpleName()).append("<");
        if (null != type.getTypeList() && !type.getTypeList().isEmpty()) {
            for (int i = 0; i < type.getTypeList().size(); i++) {
                sb.append(type.getTypeList().get(i).getSimpleName());
                if (i < type.getTypeList().size() - 1 || null != type.getChildList() && !type.getChildList().isEmpty()) {
                    sb.append(", ");
                }
            }
        }
        if (null != type.getChildList()) {
            for (ComplexSpec ct : type.getChildList()) {
                sb.append(getString(ct));
            }
        }
        sb.append(">");
        String result = sb.toString();
        if (result.contains("<>")) {
            result = result.replaceAll("<>", "<?>");
        }
        return result;
    }
}
