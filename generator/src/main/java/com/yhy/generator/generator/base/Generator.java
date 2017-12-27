package com.yhy.generator.generator.base;

import com.yhy.generator.common.Const;
import com.yhy.generator.utils.PropUtils;
import com.yhy.generator.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 15:06
 * version: 1.0.0
 * desc   :
 */
public abstract class Generator<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    private String tableName;
    private FileType fileType;
    private String baseDir;
    private String tableDir;

    public Generator(String tableName) {
        this.tableName = tableName;
        fileType = fileType();
        if (null == fileType) {
            fileType = FileType.MAPPER_XML;
        }

        baseDir();
        tableDir();
    }

    protected abstract FileType fileType();

    public abstract void generate(T file);

    private String getBaseDir() {
        return baseDir;
    }

    private String getTableDir() {
        return tableDir;
    }

    private String getRoot() {
        String root = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_PROJECT_ROOT);
        if (StringUtils.isEmpty(root)) {
            root = "/";
        }
        if (!root.endsWith("/")) {
            root += "/";
        }
        return root;
    }

    public String getPath() {
        return getRoot() + getBaseDir() + getTableDir();
    }

    private void tableDir() {
        String tableDirRule = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_TABLE_DIR_RULE);
        String tableDirReplace = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_TABLE_DIR_REPLACE);
        if (StringUtils.isEmpty(tableDirRule)) {
            tableDirRule = "_";
        }
        if (null == tableDirReplace) {
            tableDirReplace = "";
        }
        tableDir = tableName.replaceAll(tableDirRule, tableDirReplace).toLowerCase(Locale.getDefault()) + "/";
    }

    private void baseDir() {
        String subDir;
        switch (fileType) {
            case MAPPER_XML:
                subDir = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_MAPPER_XML_DIR);
                baseDir = Const.DIR_BASE_RESOURCES + normalizeDir(subDir);
                break;
            case MAPPER_JAVA:
                subDir = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_MAPPER_PACKAGE);
                baseDir = Const.DIR_BASE_JAVA + normalizeDir(subDir);
                break;
            case MODEL:
                subDir = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_MODEL_PACKAGE);
                baseDir = Const.DIR_BASE_JAVA + normalizeDir(subDir);
                break;
            case SERVICE:
                subDir = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_SUB_SERVICE_PACKAGE);
                baseDir = Const.DIR_BASE_JAVA + normalizeDir(subDir);
                break;
        }
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
        MAPPER_XML, MAPPER_JAVA, MODEL, SERVICE
    }
}
