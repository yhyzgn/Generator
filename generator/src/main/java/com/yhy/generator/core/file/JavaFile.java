package com.yhy.generator.core.file;

import com.yhy.generator.core.file.abs.AbsFile;
import com.yhy.generator.core.java.type.ImportSpec;
import com.yhy.generator.core.java.type.TypeSpec;
import com.yhy.generator.utils.StringUtils;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:19
 * version: 1.0.0
 * desc   :
 */
public class JavaFile implements AbsFile {
    private String packageName;
    private String className;
    private TypeSpec typeSpec;

    public JavaFile(String className) {
        this(null, className);
    }

    public JavaFile(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public TypeSpec getTypeSpec() {
        return typeSpec;
    }

    public void setTypeSpec(TypeSpec typeSpec) {
        this.typeSpec = typeSpec;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        sb.append("package ").append(packageName).append(";").append(lineSeparator).append(lineSeparator);

        List<Class<?>> classList = typeSpec.getClassList();
        if (null != classList) {
            for (Class<?> clazz : classList) {
                if (!StringUtils.equals("java.lang", clazz.getPackage().getName()) && !StringUtils.equals(packageName, clazz.getPackage().getName())) {
                    sb.append(new ImportSpec(clazz).string(""));
                }
            }
        }

        sb.append(lineSeparator).append(typeSpec.string(""));

        return sb.toString();
    }
}
