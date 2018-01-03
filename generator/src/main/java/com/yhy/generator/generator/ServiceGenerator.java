package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.JavaFile;
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
public class ServiceGenerator extends Generator<JavaFile> {
    private GenRecord genRecord;

    public ServiceGenerator(Table table) {
        super(table);
        genRecord = GenLoader.getInstance().get(table.getInfo().getName());
    }

    @Override
    protected FileType fileType() {
        return FileType.SERVICE;
    }

    @Override
    protected JavaFile getDataFile() {
        JavaFile javaFile = new JavaFile(getPackageName(), getServiceName());

        TypeSpec service = new TypeSpec(getServiceName());
        service.setScope(Scope.PUBLIC);
        service.setInter(true);
        GenUtils.genClassDoc(table, service);

        MethodSpec insert = new MethodSpec("insert");
        insert.setRetType(Integer.class);
        try {
            insert.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(genRecord.getModel())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        insert.addException(Exception.class);

        MethodSpec selectById = null;
        if (null != table.getPrimary()) {
            selectById = new MethodSpec("selectById");
            try {
                selectById.setRetType(Class.forName(genRecord.getModel()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            selectById.addParamSpec(new ParamSpec(table.getPrimary().getName(), GenUtils.mapColumnType(table.getPrimary())));
            selectById.addException(Exception.class);
        }

        MethodSpec selectAll = new MethodSpec("selectAll");
        try {
            selectAll.setRetComplex(new ComplexSpec(List.class).addType(Class.forName(genRecord.getModel())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selectAll.addException(Exception.class);

        MethodSpec update = new MethodSpec("update");
        update.setRetType(Integer.class);
        try {
            update.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(genRecord.getModel())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        update.addException(Exception.class);

        MethodSpec delete = new MethodSpec("delete");
        delete.setRetType(Integer.class);
        try {
            delete.addParamSpec(new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), Class.forName(genRecord.getModel())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        delete.addException(Exception.class);

        MethodSpec deleteById = null;
        if (null != table.getPrimary()) {
            deleteById = new MethodSpec("deleteById");
            deleteById.setRetType(Integer.class);
            deleteById.addParamSpec(new ParamSpec(table.getPrimary().getName(), GenUtils.mapColumnType(table.getPrimary())));
            deleteById.addException(Exception.class);
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

        genRecord.setService(getPackageName() + "." + getServiceName());
        GenLoader.getInstance().save(genRecord);
        return javaFile;
    }
}
