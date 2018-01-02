package com.yhy.generator.generator;

import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.GenUtils;

import java.util.List;
import java.util.Locale;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-02 16:00
 * version: 1.0.0
 * desc   :
 */
public class MapperGenerator extends Generator<JavaFile> {
    private String referenceModel;

    public MapperGenerator(Table table, String referenceModel) {
        super(table);
        this.referenceModel = referenceModel;
    }

    @Override
    protected FileType fileType() {
        return FileType.MAPPER_JAVA;
    }

    @Override
    protected JavaFile getDataFile() {
        JavaFile javaFile = new JavaFile(getPackageName(), getMapperName());

        TypeSpec mapper = new TypeSpec(getMapperName());
        mapper.setScope(Scope.PUBLIC);
        mapper.setInter(true);
        GenUtils.genClassAnno(table, mapper);
        try {
            mapper.addAnnoSpec(new AnnoSpec(Class.forName("org.springframework.stereotype.Repository")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MethodSpec insert = new MethodSpec("insert");
        insert.setRetType(Integer.class);
        try {
            insert.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(referenceModel)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MethodSpec selectById = null;
        if (null != table.getPrimary()) {
            selectById = new MethodSpec("selectById");
            try {
                selectById.setRetType(Class.forName(referenceModel));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            selectById.addParamSpec(new ParamSpec(table.getPrimary().getName(), GenUtils.mapColumnType(table.getPrimary())));
        }

        MethodSpec selectAll = new MethodSpec("selectAll");
        try {
            selectAll.setRetComplex(new ComplexSpec(List.class).addType(Class.forName(referenceModel)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MethodSpec update = new MethodSpec("update");
        update.setRetType(Integer.class);
        try {
            update.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(referenceModel)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MethodSpec delete = new MethodSpec("delete");
        delete.setRetType(Integer.class);
        try {
            delete.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(referenceModel)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MethodSpec deleteById = null;

        if (null != table.getPrimary()) {
            deleteById = new MethodSpec("deleteById");
            deleteById.setRetType(Integer.class);
            deleteById.addParamSpec(new ParamSpec(table.getPrimary().getName(), GenUtils.mapColumnType(table.getPrimary())));
        }

        mapper.addMethodSpec(insert);
        if (null != selectById) {
            mapper.addMethodSpec(selectById);
        }
        mapper.addMethodSpec(selectAll);
        mapper.addMethodSpec(update);
        mapper.addMethodSpec(delete);
        if (null != deleteById) {
            mapper.addMethodSpec(deleteById);
        }

        javaFile.setTypeSpec(mapper);
        return javaFile;
    }
}
