package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 15:04
 * version: 1.0.0
 * desc   :
 */
public class AnnoSpec implements AbsSpec {
    private Class<?> type;
    private String[] args;

    public AnnoSpec(Class<?> type, String... args) {
        this.type = type;
        this.args = args;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("@").append(type.getSimpleName());
        if (null != args && args.length > 0) {
            sb.append("(");
            if (args.length == 1) {
                sb.append("\"").append(args[0]).append("\"");
            } else {
                sb.append("{");
                for (int i = 0; i < args.length; i++) {
                    sb.append("\"").append(args[i]).append("\"");
                    if (i < args.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("}");
            }
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public List<Class<?>> getClassList() {
        if (null != type) {
            List<Class<?>> result = new ArrayList<>();
            result.add(type);
            return result;
        }
        return null;
    }
}
