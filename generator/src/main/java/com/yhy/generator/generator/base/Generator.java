package com.yhy.generator.generator.base;

import com.yhy.generator.common.Const;
import com.yhy.generator.core.file.abs.AbsFile;
import com.yhy.generator.helper.FileWriter;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.PropUtils;
import com.yhy.generator.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.Locale;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 15:06
 * version: 1.0.0
 * desc   :
 */
public abstract class Generator<T extends AbsFile> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    protected Table table;
    protected FileType fileType;
    protected String dir;
    protected String packageName;
    protected String modelName;

    public Generator(Table table) {
        this.table = table;
        fileType = fileType();
        if (null == fileType) {
            fileType = FileType.MODEL;
        }

        dir();
        tableDir();
    }

    protected abstract FileType fileType();

    protected abstract T getDataFile();

    public T generate() {
        return generate(null, null);
    }

    public T generate(OutputStream os) {
        return generate(os, null);
    }

    public T generate(FileWriter.OnWriteListener listener) {
        return generate(null, listener);
    }

    public T generate(OutputStream os, FileWriter.OnWriteListener listener) {
        T dataFile = getDataFile();
        FileWriter<T> writer = new FileWriter<>(this, dataFile);
        writer.write(os, listener);
        return dataFile;
    }

    public String getDir() {
        return dir;
    }

    public String getRoot() {
        String root = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_PROJECT_ROOT);
        if (StringUtils.isEmpty(root)) {
            root = "/";
        }
        if (!root.endsWith("/")) {
            root += "/";
        }
        return root;
    }

    public String getModelFileName() {
        return modelName + ".java";
    }

    public String getModelName() {
        return modelName;
    }

    public String getMapperXmlFileName() {
        return modelName + "Mapper.xml";
    }

    public String getMapperXmlName() {
        return modelName + "Mapper";
    }

    public String getMapperFileName() {
        return modelName + "Mapper.java";
    }

    public String getMapperName() {
        return modelName + "Mapper";
    }

    public String getServiceFileName() {
        return modelName + "Service.java";
    }

    public String getServiceName() {
        return modelName + "Service";
    }

    public String getServiceImplFileName() {
        return modelName + "ServiceImpl.java";
    }

    public String getServiceImplName() {
        return modelName + "ServiceImpl";
    }

    public String getPackageName() {
        return packageName;
    }

    public FileType getFileType() {
        return fileType;
    }

    private String tableDir() {
        String tableDirRule = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_TABLE_DIR_RULE);
        String tableDirReplace = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_TABLE_DIR_REPLACE);
        if (StringUtils.isEmpty(tableDirRule)) {
            tableDirRule = "_";
        }
        if (null == tableDirReplace) {
            tableDirReplace = "";
        }
        String tableDir = table.getInfo().getName().replaceAll(tableDirRule, tableDirReplace).toLowerCase(Locale.getDefault());
        tableDir = tableDir.replaceAll("_", "");

        modelName = ConvertUtils.caseFirstCharUpper(tableDir);

        return tableDir;
    }

    private void dir() {
        switch (fileType) {
            case MODEL:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_MODEL);
                packageName += "." + tableDir();
                dir = Const.DIR_BASE_JAVA + normalizeDir(packageName);
                break;
            case MAPPER_XML:
                String subDir = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_DIR_MAPPER_XML);
                dir = Const.DIR_BASE_RESOURCES + normalizeDir(subDir);
                break;
            case MAPPER_JAVA:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_MAPPER);
                packageName += "." + tableDir();
                dir = Const.DIR_BASE_JAVA + normalizeDir(packageName);
                break;
            case SERVICE:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_SERVICE);
                packageName += "." + tableDir();
                dir = Const.DIR_BASE_JAVA + normalizeDir(packageName);
                break;
            case SERVICE_IMPL:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_SERVICE);
                packageName += "." + tableDir() + ".impl";
                dir = Const.DIR_BASE_JAVA + normalizeDir(packageName);
                break;
        }

        LOGGER.info("File dir is " + dir);
    }

    private String normalizeDir(String subDir) {
        if (null == subDir || StringUtils.equals("/", subDir)) {
            subDir = "";
        }
        subDir = subDir.replaceAll("\\.", "/");
        subDir = subDir.replaceAll("\\\\", "/");
        if (subDir.startsWith("/")) {
            subDir.substring(1);
        }
        if (!subDir.endsWith("/")) {
            subDir += "/";
        }
        return subDir;
    }

    public enum FileType {
        MAPPER_XML, MAPPER_JAVA, MODEL, SERVICE, SERVICE_IMPL
    }
}
