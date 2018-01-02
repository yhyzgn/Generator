package com.yhy.generator.generator;

import com.yhy.generator.common.Const;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.GenUtils;
import com.yhy.generator.utils.PropUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        type.addDocSpec(new DocSpec("author :", PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_AUTHOR_NAME, "颜洪毅")));
        type.addDocSpec(new DocSpec("e-mail :", PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_AUTHOR_EMAIL, "yhyzgn@gmail.com")));
        type.addDocSpec(new DocSpec("time   :", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        type.addDocSpec(new DocSpec("version:", "1.0.0"));
        type.addDocSpec(new DocSpec("desc   :", table.getInfo().getComment()));

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
        return javaFile;
    }
}
