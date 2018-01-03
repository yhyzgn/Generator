package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.*;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.model.GenRecord;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.GenUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-02 16:00
 * version: 1.0.0
 * desc   :
 */
public class ServiceImplGenerator extends Generator<JavaFile> {
    private GenRecord genRecord;
    private String mapperName;

    public ServiceImplGenerator(Table table) {
        super(table);
        genRecord = GenLoader.getInstance().get(table.getInfo().getName());
    }

    @Override
    protected FileType fileType() {
        return FileType.SERVICE_IMPL;
    }

    @Override
    protected JavaFile getDataFile() {
        JavaFile javaFile = new JavaFile(getPackageName(), getServiceImplName());

        TypeSpec service = new TypeSpec(getServiceImplName());
        service.setScope(Scope.PUBLIC);
        GenUtils.genClassDoc(table, service);

        try {
            service.addInter(Class.forName(genRecord.getService()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            service.addAnnoSpec(new AnnoSpec(Class.forName("org.springframework.stereotype.Service")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        FieldSpec mapper = null;
        try {
            Class<?> mapperClass = Class.forName(genRecord.getMapper());
            mapperName = ConvertUtils.caseFirstCharLower(mapperClass.getSimpleName());
            mapper = new FieldSpec(mapperName);
            mapper.setScope(Scope.PRIVATE).addAnnoSpec(new AnnoSpec(Resource.class)).setType(mapperClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MethodSpec insert = new MethodSpec("insert");
        insert.setScope(Scope.PUBLIC).setRetType(Integer.class);
        try {
            ParamSpec paramSpec = new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(genRecord.getModel()));
            insert.addParamSpec(paramSpec);
            insert.addStMentSpec(new StMentSpec("return " + mapperName + ".insert(" + paramSpec.getName() + ")"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        insert.addException(Exception.class);

        MethodSpec selectById = null;
        if (null != table.getPrimary()) {
            selectById = new MethodSpec("selectById");
            try {
                selectById.setScope(Scope.PUBLIC).setRetType(Class.forName(genRecord.getModel()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ParamSpec paramSpec = new ParamSpec(table.getPrimary().getName(), GenUtils.mapColumnType(table.getPrimary()));
            selectById.addParamSpec(paramSpec);
            selectById.addStMentSpec(new StMentSpec("return " + mapperName + ".selectById(" + paramSpec.getName() + ")"));
            selectById.addException(Exception.class);
        }

        MethodSpec selectAll = new MethodSpec("selectAll");
        try {
            selectAll.setScope(Scope.PUBLIC).setRetComplex(new ComplexSpec(List.class).addType(Class.forName(genRecord.getModel())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selectAll.addStMentSpec(new StMentSpec("return " + mapperName + ".selectAll()"));
        selectAll.addException(Exception.class);

        MethodSpec update = new MethodSpec("update");
        update.setScope(Scope.PUBLIC).setRetType(Integer.class);
        try {
            ParamSpec paramSpec = new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(genRecord.getModel()));
            update.addParamSpec(paramSpec);
            update.addStMentSpec(new StMentSpec("return " + mapperName + ".update(" + paramSpec.getName() + ")"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        update.addException(Exception.class);

        MethodSpec delete = new MethodSpec("delete");
        delete.setScope(Scope.PUBLIC).setRetType(Integer.class);
        try {
            ParamSpec paramSpec = new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(genRecord.getModel()));
            delete.addParamSpec(paramSpec);
            delete.addStMentSpec(new StMentSpec("return " + mapperName + ".delete(" + paramSpec.getName() + ")"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        delete.addException(Exception.class);

        MethodSpec deleteById = null;
        if (null != table.getPrimary()) {
            deleteById = new MethodSpec("deleteById");
            deleteById.setScope(Scope.PUBLIC).setRetType(Integer.class);
            ParamSpec paramSpec = new ParamSpec(table.getPrimary().getName(), GenUtils.mapColumnType(table.getPrimary()));
            deleteById.addParamSpec(paramSpec);
            deleteById.addStMentSpec(new StMentSpec("return " + mapperName + ".deleteById(" + paramSpec.getName() + ")"));
            deleteById.addException(Exception.class);
        }

        if (null != mapper) {
            service.addFieldSpec(mapper);
        }
        service.addMethodSpec(insert);
        if (null != selectById) {
            service.addMethodSpec(selectById);
        }
        service.addMethodSpec(selectAll);
        service.addMethodSpec(update);
        service.addMethodSpec(delete);
        if (null != deleteById) {
            service.addMethodSpec(deleteById);
        }

        javaFile.setTypeSpec(service);

        genRecord.setServiceImpl(getPackageName() + "." + getServiceImplName());
        GenLoader.getInstance().save(genRecord);

        return javaFile;
    }
}
