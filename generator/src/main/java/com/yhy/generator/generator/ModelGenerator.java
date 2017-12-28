package com.yhy.generator.generator;

import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Modifier;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.helper.FileWriter;
import com.yhy.generator.model.table.Table;
import jdk.nashorn.internal.ir.annotations.Reference;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.servlet.annotation.WebListener;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    public void generate() {
        FileWriter<JavaFile> writer = new FileWriter<>(this, genJava());
        writer.write(System.out);
    }

    private JavaFile genJava() {
        JavaFile javaFile = new JavaFile(getPackageName(), getModelName());

        FieldSpec username = new FieldSpec("username");
        username.setDocSpec(new DocSpec("用户名"));
        username.setScope(Scope.PRIVATE);
        username.setType(String.class);
        username.addAnnoSpec(new AnnoSpec(WebListener.class));

        FieldSpec age = new FieldSpec("age");
        age.setDocSpec(new DocSpec("年龄"));
        age.setScope(Scope.PRIVATE);
        age.setComplexType(new ComplexType(List.class).addChild(new ComplexType(Map.class).addType(String.class).addType(Integer.class)));

        TypeSpec type = new TypeSpec(getModelName());
        type.setScope(Scope.PUBLIC);
        type.addModifier(Modifier.FINAL);
        type.setExtClass(Object.class);
        type.addInter(Serializable.class);
        type.addInter(Context.class);
        type.addDocSpec(new DocSpec("author :", "颜洪毅"));
        type.addDocSpec(new DocSpec("e-mail :", "yhyzgn@gmail.com"));
        type.addDocSpec(new DocSpec("time   :", "2017-12-28 16:12:32"));
        type.addDocSpec(new DocSpec("version:", "1.0.0"));
        type.addDocSpec(new DocSpec("desc   :", "测试类"));
        type.addAnnoSpec(new AnnoSpec(Resource.class));
        type.addAnnoSpec(new AnnoSpec(Reference.class));

        type.addFieldSpec(username);
        type.addFieldSpec(age);

        javaFile.setTypeSpec(type);

        return javaFile;
    }
}
