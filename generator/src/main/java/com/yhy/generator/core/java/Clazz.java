package com.yhy.generator.core.java;

import com.yhy.generator.utils.StringUtils;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-04 9:19
 * version: 1.0.0
 * desc   :
 */
public class Clazz {
    private String name;
    private String packageName;
    private String simpleName;

    public Clazz(Class<?> clazz) {
        this(clazz.getName());
    }

    public Clazz(String name) {
        this.name = name;
        if (StringUtils.isNotEmpty(this.name) && this.name.contains(".")) {
            int lastIndex = this.name.lastIndexOf(".");
            packageName = this.name.substring(0, lastIndex);
            simpleName = this.name.substring(lastIndex + 1);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
}
