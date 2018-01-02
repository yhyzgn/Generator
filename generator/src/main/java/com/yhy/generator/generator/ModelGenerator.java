package com.yhy.generator.generator;

import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.GenUtils;

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
        type.addDocSpec(new DocSpec("author :", "颜洪毅"));
        type.addDocSpec(new DocSpec("e-mail :", "yhyzgn@gmail.com"));
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

//        FieldSpec username = new FieldSpec("username");
//        username.setDocSpec(new DocSpec("用户名"));
//        username.setScope(Scope.PRIVATE);
//        username.setType(String.class);
////        username.addAnnoSpec(new AnnoSpec(WebListener.class));
//
//        FieldSpec age = new FieldSpec("age");
//        age.setDocSpec(new DocSpec("年龄"));
//        age.setScope(Scope.PRIVATE);
//        age.setComplexSpec(new ComplexSpec(List.class).addChild(new ComplexSpec(Map.class).addType(String.class).addType(Integer.class)));
//
//        FieldSpec test = new FieldSpec("test");
//        test.setDocSpec(new DocSpec("测试"));
//        test.setScope(Scope.PUBLIC);
////        test.addModifier(Modifier.STATIC).addModifier(Modifier.FINAL);
//        test.setComplexSpec(new ComplexSpec(Map.class).addType(String.class).addChild(new ComplexSpec(List.class).addChild(new ComplexSpec(Map.class).addType(String.class).addType(Integer.class))));

//        MethodSpec method = new MethodSpec("getUser");
//        method
//                .setScope(Scope.PUBLIC)
//                .addModifier(Modifier.FINAL)
//                .setRetType(String.class)
//                .addParamSpec(new ParamSpec("user", String.class));
//
//        method.addDocSpec(new DocSpec("@param", "user"));
//        method.addDocSpec(new DocSpec("@param", "test"));
//
//        ParamSpec paramSpec = new ParamSpec("test", String.class);
//        try {
//            paramSpec.setAnnoSpec(new AnnoSpec(Class.forName("org.apache.ibatis.annotations.Param"), "record"));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        method.addParamSpec(paramSpec);
//
//        StMentSpec stIf = new StMentSpec("if(null != test)");
//        stIf.addStMentSpec(new StMentSpec("$T.out.println(test)").format(System.class));
//        stIf.addStMentSpec(new StMentSpec("$T.out.println($S)").format(System.class, "哈哈哈"));
//        StMentSpec stElse = new StMentSpec("else");
//        stElse.addStMentSpec(new StMentSpec("$T.out.println($S)").format(System.class, "user为空"));
//
//        method.addStMentSpec(stIf).addStMentSpec(stElse);
//        method.addStMentSpec(new StMentSpec("return test"));

//        TypeSpec type = new TypeSpec(getModelName());
//        type.setScope(Scope.PUBLIC);
//        type.addModifier(Modifier.FINAL);
//        type.setExtClass(Object.class);
//        type.addInter(Serializable.class);
//        type.addInter(Context.class);
//        type.addDocSpec(new DocSpec("author :", "颜洪毅"));
//        type.addDocSpec(new DocSpec("e-mail :", "yhyzgn@gmail.com"));
//        type.addDocSpec(new DocSpec("time   :", "2017-12-28 16:12:32"));
//        type.addDocSpec(new DocSpec("version:", "1.0.0"));
//        type.addDocSpec(new DocSpec("desc   :", "测试类"));
//        type.addAnnoSpec(new AnnoSpec(Resource.class, "name"));
//        type.addAnnoSpec(new AnnoSpec(Reference.class, "age", "test"));

//        type.addFieldSpec(username);
//        type.addFieldSpec(age);
//        type.addFieldSpec(test);
//        type.addMethodSpec(method);

        javaFile.setTypeSpec(type);

        return javaFile;
    }
}
