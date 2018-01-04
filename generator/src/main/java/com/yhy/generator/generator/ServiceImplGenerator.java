package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.java.Clazz;
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
        service.addInter(new Clazz(genRecord.getService()));
        service.addAnnoSpec(new AnnoSpec(new Clazz("org.springframework.stereotype.Service")));

        FieldSpec mapper = null;
        Clazz mapperClazz = new Clazz(genRecord.getMapper());
        mapperName = ConvertUtils.caseFirstCharLower(mapperClazz.getSimpleName());
        mapper = new FieldSpec(mapperName);
        mapper.setScope(Scope.PRIVATE).addAnnoSpec(new AnnoSpec(new Clazz(Resource.class))).setType(mapperClazz);

        MethodSpec insert = new MethodSpec("insert");
        insert.setScope(Scope.PUBLIC).setRetType(new Clazz(Integer.class));
        ParamSpec paramInsert = new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), new Clazz(genRecord.getModel()));
        insert.addParamSpec(paramInsert);
        insert.addStMentSpec(new StMentSpec("return " + mapperName + ".insert(" + paramInsert.getName() + ")"));
        insert.addException(new Clazz(Exception.class));

        MethodSpec selectById = null;
        if (null != table.getPrimary()) {
            selectById = new MethodSpec("selectById");
            selectById.setScope(Scope.PUBLIC).setRetType(new Clazz(genRecord.getModel()));
            ParamSpec paramSelectById = new ParamSpec(table.getPrimary().getName(), new Clazz(GenUtils.mapColumnType(table.getPrimary())));
            selectById.addParamSpec(paramSelectById);
            selectById.addStMentSpec(new StMentSpec("return " + mapperName + ".selectById(" + paramSelectById.getName() + ")"));
            selectById.addException(new Clazz(Exception.class));
        }

        MethodSpec selectAll = new MethodSpec("selectAll");
        selectAll.setScope(Scope.PUBLIC).setRetComplex(new ComplexSpec(new Clazz(List.class)).addType(new Clazz(genRecord.getModel())));
        selectAll.addStMentSpec(new StMentSpec("return " + mapperName + ".selectAll()"));
        selectAll.addException(new Clazz(Exception.class));

        MethodSpec update = new MethodSpec("update");
        update.setScope(Scope.PUBLIC).setRetType(new Clazz(Integer.class));
        ParamSpec paramUpdate = new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), new Clazz(genRecord.getModel()));
        update.addParamSpec(paramUpdate);
        update.addStMentSpec(new StMentSpec("return " + mapperName + ".update(" + paramUpdate.getName() + ")"));
        update.addException(new Clazz(Exception.class));

        MethodSpec delete = new MethodSpec("delete");
        delete.setScope(Scope.PUBLIC).setRetType(new Clazz(Integer.class));
        ParamSpec paramDelete = new ParamSpec(getModelName().toLowerCase(Locale.getDefault()), new Clazz(genRecord.getModel()));
        delete.addParamSpec(paramDelete);
        delete.addStMentSpec(new StMentSpec("return " + mapperName + ".delete(" + paramDelete.getName() + ")"));
        delete.addException(new Clazz(Exception.class));

        MethodSpec deleteById = null;
        if (null != table.getPrimary()) {
            deleteById = new MethodSpec("deleteById");
            deleteById.setScope(Scope.PUBLIC).setRetType(new Clazz(Integer.class));
            ParamSpec paramDeleteById = new ParamSpec(table.getPrimary().getName(), new Clazz(GenUtils.mapColumnType(table.getPrimary())));
            deleteById.addParamSpec(paramDeleteById);
            deleteById.addStMentSpec(new StMentSpec("return " + mapperName + ".deleteById(" + paramDeleteById.getName() + ")"));
            deleteById.addException(new Clazz(Exception.class));
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
