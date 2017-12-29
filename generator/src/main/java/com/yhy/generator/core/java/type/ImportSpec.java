package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.type.abs.AbsSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:28
 * version: 1.0.0
 * desc   :
 */
public class ImportSpec implements AbsSpec {
    private Class<?> type;

    public ImportSpec(Class<?> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "import " + type.getName() + ";" + System.getProperty("line.separator");
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
