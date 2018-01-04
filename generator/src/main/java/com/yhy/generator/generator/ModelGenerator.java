package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.GenRecord;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.GenUtils;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 14:18
 * version: 1.0.0
 * desc   : Model 生成器
 */
public class ModelGenerator extends Generator<JavaFile> {
    public ModelGenerator(Table table) {
        super(table);
    }

    @Override
    protected FileType fileType() {
        return FileType.MODEL;
    }

    @Override
    public JavaFile getDataFile() {
        return genJava();
    }

    private JavaFile genJava() {
        if (null == table) {
            return null;
        }

        JavaFile javaFile = new JavaFile(getPackageName(), getModelName());

        // Model类
        TypeSpec type = new TypeSpec(getModelName());
        type.setScope(Scope.PUBLIC);
        GenUtils.genClassDoc(table, type);

        if (null != table.getColumnList() && !table.getColumnList().isEmpty()) {
            FieldSpec field;
            MethodSpec getter;
            MethodSpec setter;
            for (Column column : table.getColumnList()) {
                // 添加字段
                field = new FieldSpec(column.getName());
                field.setDocSpec(new DocSpec(column.getComment()));
                field.setScope(Scope.PRIVATE);
                field.setType(new Clazz(GenUtils.mapColumnType(column)));

                // 添加get方法
                getter = new MethodSpec("get" + ConvertUtils.caseFirstCharUpper(field.getName()));
                getter.setScope(Scope.PUBLIC).setRetType(field.getType());
                getter.addStMentSpec(new StMentSpec("return " + field.getName()));

                // 添加set方法
                setter = new MethodSpec("set" + ConvertUtils.caseFirstCharUpper(field.getName()));
                setter.setScope(Scope.PUBLIC).setRetType(new Clazz(Void.class));
                setter.addParamSpec(new ParamSpec(field.getName(), field.getType()));
                setter.addStMentSpec(new StMentSpec("this." + field.getName() + " = " + field.getName()));

                // 将字段和方法都添加到类中
                type.addFieldSpec(field);
                type.addMethodSpec(getter);
                type.addMethodSpec(setter);
            }
        }

        // 将类设置到java文件中
        javaFile.setTypeSpec(type);

        // 创建生成记录，并保存
        GenRecord record = new GenRecord();
        record.setTable(table.getInfo().getName());
        record.setModel(getPackageName() + "." + getModelName());
        GenLoader.getInstance().save(record);

        return javaFile;
    }
}
