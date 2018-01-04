package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Clazz;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.GenRecord;
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
    private GenRecord genRecord;

    public MapperGenerator(Table table) {
        super(table);
        genRecord = GenLoader.getInstance().get(table.getInfo().getName());
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
        GenUtils.genClassDoc(table, mapper);
        mapper.addAnnoSpec(new AnnoSpec(new Clazz("org.springframework.stereotype.Repository")));

        MethodSpec insert = new MethodSpec("insert");
        insert.setRetType(new Clazz(Integer.class));
        insert.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), new Clazz(genRecord.getModel())));
        insert.addException(new Clazz(Exception.class));

        MethodSpec selectById = null;
        if (null != table.getPrimary()) {
            selectById = new MethodSpec("selectById");
            selectById.setRetType(new Clazz(genRecord.getModel()));
            selectById.addParamSpec(new ParamSpec(table.getPrimary().getName(), new Clazz(GenUtils.mapColumnType(table.getPrimary()))));
            selectById.addException(new Clazz(Exception.class));
        }

        MethodSpec selectAll = new MethodSpec("selectAll");
        selectAll.setRetComplex(new ComplexSpec(new Clazz(List.class)).addType(new Clazz(genRecord.getModel())));
        selectAll.addException(new Clazz(Exception.class));

        MethodSpec update = new MethodSpec("update");
        update.setRetType(new Clazz(Integer.class));
        update.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), new Clazz(genRecord.getModel())));
        update.addException(new Clazz(Exception.class));

        MethodSpec delete = new MethodSpec("delete");
        delete.setRetType(new Clazz(Integer.class));
        delete.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), new Clazz(genRecord.getModel())));
        delete.addException(new Clazz(Exception.class));

        MethodSpec deleteById = null;
        if (null != table.getPrimary()) {
            deleteById = new MethodSpec("deleteById");
            deleteById.setRetType(new Clazz(Integer.class));
            deleteById.addParamSpec(new ParamSpec(table.getPrimary().getName(), new Clazz(GenUtils.mapColumnType(table.getPrimary()))));
            deleteById.addException(new Clazz(Exception.class));
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

        genRecord.setMapper(getPackageName() + "." + getMapperName());
        GenLoader.getInstance().save(genRecord);

        return javaFile;
    }
}
