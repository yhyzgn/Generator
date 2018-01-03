package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.JavaFile;
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
 * desc   :
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

        TypeSpec type = new TypeSpec(getModelName());
        type.setScope(Scope.PUBLIC);
        GenUtils.genClassDoc(table, type);

        if (null != table.getColumnList() && !table.getColumnList().isEmpty()) {
            FieldSpec field;
            MethodSpec getter;
            MethodSpec setter;
            for (Column column : table.getColumnList()) {
                field = new FieldSpec(column.getName());
                field.setDocSpec(new DocSpec(column.getComment()));
                field.setScope(Scope.PRIVATE);
                field.setType(GenUtils.mapColumnType(column));

                getter = new MethodSpec("get" + ConvertUtils.caseFirstCharUpper(field.getName()));
                getter.setScope(Scope.PUBLIC).setRetType(field.getType());
                getter.addStMentSpec(new StMentSpec("return " + field.getName()));

                setter = new MethodSpec("set" + ConvertUtils.caseFirstCharUpper(field.getName()));
                setter.setScope(Scope.PUBLIC).setRetType(Void.class);
                setter.addParamSpec(new ParamSpec(field.getName(), field.getType()));
                setter.addStMentSpec(new StMentSpec("this." + field.getName() + " = " + field.getName()));

                type.addFieldSpec(field);
                type.addMethodSpec(getter);
                type.addMethodSpec(setter);
            }
        }

        javaFile.setTypeSpec(type);

        GenRecord record = new GenRecord();
        record.setTable(table.getInfo().getName());
        record.setModel(getPackageName() + "." + getModelName());
        GenLoader.getInstance().save(record);

        return javaFile;
    }
}
