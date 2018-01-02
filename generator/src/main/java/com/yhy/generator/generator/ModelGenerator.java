package com.yhy.generator.generator;

import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Modifier;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.table.Table;
import jdk.nashorn.internal.ir.annotations.Reference;

import javax.servlet.annotation.WebListener;
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
    public JavaFile getDataFile() {
        return genJava();
    }

    private JavaFile genJava() {
        JavaFile javaFile = new JavaFile(getPackageName(), getModelName());

        FieldSpec username = new FieldSpec("username");
        username.setDocSpec(new DocSpec("用户名"));
        username.setScope(Scope.PRIVATE);
        username.setType(String.class);
//        username.addAnnoSpec(new AnnoSpec(WebListener.class));

        FieldSpec age = new FieldSpec("age");
        age.setDocSpec(new DocSpec("年龄"));
        age.setScope(Scope.PRIVATE);
        age.setComplexSpec(new ComplexSpec(List.class).addChild(new ComplexSpec(Map.class).addType(String.class).addType(Integer.class)));

        FieldSpec test = new FieldSpec("test");
        test.setDocSpec(new DocSpec("测试"));
        test.setScope(Scope.PUBLIC);
//        test.addModifier(Modifier.STATIC).addModifier(Modifier.FINAL);
        test.setComplexSpec(new ComplexSpec(Map.class).addType(String.class).addChild(new ComplexSpec(List.class).addChild(new ComplexSpec(Map.class).addType(String.class).addType(Integer.class))));

        MethodSpec method = new MethodSpec("getUser");
        method
                .setScope(Scope.PUBLIC)
                .addModifier(Modifier.FINAL)
                .setRetType(String.class)
                .addParamSpec(new ParamSpec("user", String.class));

        method.addDocSpec(new DocSpec("@param", "user"));
        method.addDocSpec(new DocSpec("@param", "test"));

        ParamSpec paramSpec = new ParamSpec("test", String.class);
        try {
            paramSpec.setAnnoSpec(new AnnoSpec(Class.forName("org.apache.ibatis.annotations.Param"), "record"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        method.addParamSpec(paramSpec);

        StMentSpec stIf = new StMentSpec("if(null != test)");
        stIf.addStMentSpec(new StMentSpec("$T.out.println(test)").format(System.class));
        stIf.addStMentSpec(new StMentSpec("$T.out.println($S)").format(System.class, "哈哈哈"));
        StMentSpec stElse = new StMentSpec("else");
        stElse.addStMentSpec(new StMentSpec("$T.out.println($S)").format(System.class, "user为空"));

        method.addStMentSpec(stIf).addStMentSpec(stElse);
        method.addStMentSpec(new StMentSpec("return test"));

        TypeSpec type = new TypeSpec(getModelName());
        type.setScope(Scope.PUBLIC);
        type.addModifier(Modifier.FINAL);
//        type.setExtClass(Object.class);
//        type.addInter(Serializable.class);
//        type.addInter(Context.class);
        type.addDocSpec(new DocSpec("author :", "颜洪毅"));
        type.addDocSpec(new DocSpec("e-mail :", "yhyzgn@gmail.com"));
        type.addDocSpec(new DocSpec("time   :", "2017-12-28 16:12:32"));
        type.addDocSpec(new DocSpec("version:", "1.0.0"));
        type.addDocSpec(new DocSpec("desc   :", "测试类"));
//        type.addAnnoSpec(new AnnoSpec(Resource.class, "name"));
//        type.addAnnoSpec(new AnnoSpec(Reference.class, "age", "test"));

        type.addFieldSpec(username);
        type.addFieldSpec(age);
        type.addFieldSpec(test);
        type.addMethodSpec(method);

        javaFile.setTypeSpec(type);

        return javaFile;
    }
}
