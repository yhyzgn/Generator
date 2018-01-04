package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.Clazz;
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
    private Clazz type;

    public ImportSpec(Clazz type) {
        this.type = type;
    }

    @Override
    public List<Clazz> getClassList() {
        if (null != type) {
            List<Clazz> result = new ArrayList<>();
            result.add(type);
            return result;
        }
        return null;
    }

    @Override
    public String string(String indent) {
        return "import " + type.getName() + ";" + System.getProperty("line.separator");
    }
}
