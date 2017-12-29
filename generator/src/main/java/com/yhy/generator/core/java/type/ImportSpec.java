package com.yhy.generator.core.java.type;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:28
 * version: 1.0.0
 * desc   :
 */
public class ImportSpec {
    private Class<?> type;

    public ImportSpec(Class<?> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "import " + type.getName() + ";" + System.getProperty("line.separator");
    }
}