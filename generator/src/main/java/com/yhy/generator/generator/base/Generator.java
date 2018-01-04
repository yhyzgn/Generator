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
 * desc   : 代码生成器基类
 */
public abstract class Generator<T extends AbsFile> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);
    // 表
    protected Table table;
    // 生成文件类型
    private FileType fileType;
    // 生成目录
    private String dir;
    // 如果是mapper xml，记录mapper xml的目录
    private String mapperXmlDir;
    // 生成java类时，记录类的包名
    private String packageName;
    // 表对应Model的名称
    private String modelName;

    public Generator(Table table) {
        this.table = table;
        fileType = fileType();
        if (null == fileType) {
            fileType = FileType.MODEL;
        }

        dir();
        tableDir();
    }

    /**
     * 由子类设定文件类型
     *
     * @return 文件类型
     */
    protected abstract FileType fileType();

    /**
     * 获取到具体的文件
     *
     * @return 具体的文件
     */
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
        // 生成文件
        writer.write(os, listener);
        return dataFile;
    }

    /**
     * 获取到应该生成代码的完整目录
     *
     * @return 完整目录
     */
    public String getDir() {
        return dir;
    }

    /**
     * 获取到项目或者模块根目录
     *
     * @return 根目录
     */
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

    public String getMapperXmlDir() {
        return mapperXmlDir;
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

    /**
     * 按表名生成子目录
     *
     * @return 子目录名称
     */
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

    /**
     * 生成完整的目录
     */
    private void dir() {
        switch (fileType) {
            case MODEL:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_MODEL);
                packageName += "." + tableDir();
                dir = Const.DIR_BASE_JAVA + packageToDir(packageName);
                break;
            case MAPPER_JAVA:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_MAPPER);
                packageName += "." + tableDir();
                packageName = normalizePackage(packageName);
                dir = Const.DIR_BASE_JAVA + packageToDir(packageName);
                break;
            case MAPPER_XML:
                mapperXmlDir = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_DIR_MAPPER_XML);
                mapperXmlDir += "/" + tableDir();
                mapperXmlDir = normalizeDir(mapperXmlDir);
                dir = Const.DIR_BASE_RESOURCES + packageToDir(mapperXmlDir);
                break;
            case SERVICE:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_SERVICE);
                packageName += "." + tableDir();
                packageName = normalizePackage(packageName);
                dir = Const.DIR_BASE_JAVA + packageToDir(packageName);
                break;
            case SERVICE_IMPL:
                packageName = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_PACKAGE_SERVICE);
                packageName += "." + tableDir() + ".impl";
                packageName = normalizePackage(packageName);
                dir = Const.DIR_BASE_JAVA + packageToDir(packageName);
                break;
        }

        LOGGER.info("File dir is " + dir);
    }

    /**
     * 规范包名
     *
     * @param packageName 包名
     * @return 规范后的包名
     */
    private String normalizePackage(String packageName) {
        if (null == packageName) {
            packageName = "";
        }
        return packageName.replaceAll("\\.\\.+", "\\.");
    }

    /**
     * 规范目录路径
     *
     * @param path 路径
     * @return 规范后的路径
     */
    private String normalizeDir(String path) {
        if (null == path) {
            path = "/";
        }
        return path.replaceAll("//+", "/");
    }

    /**
     * 将包名转换为目录
     *
     * @param packageName 包名
     * @return 目录
     */
    private String packageToDir(String packageName) {
        if (null == packageName) {
            packageName = "";
        }
        packageName = packageName.replaceAll("\\.", "/");
        packageName = packageName.replaceAll("\\\\", "/");
        packageName = packageName.replaceAll("//+", "/");
        if (packageName.startsWith("/")) {
            packageName.substring(1);
        }
        if (!packageName.endsWith("/")) {
            packageName += "/";
        }
        return packageName;
    }

    /**
     * 各种文件类型
     */
    public enum FileType {
        MAPPER_XML, MAPPER_JAVA, MODEL, SERVICE, SERVICE_IMPL
    }
}
