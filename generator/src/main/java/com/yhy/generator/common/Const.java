package com.yhy.generator.common;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 13:50
 * version: 1.0.0
 * desc   :
 */
public interface Const {
    String INITIALIZER_PROPERTIES = "genConfig";

    String PROP_GEN_ENABLE = "gen.enable";
    String PROP_GEN_PROJECT_ROOT = "gen.project.root";

    String PROP_GEN_SUB_MAPPER_XML_DIR = "gen.sub.mapper.xml.dir";
    String PROP_GEN_SUB_MAPPER_PACKAGE = "gen.sub.mapper.package";
    String PROP_GEN_SUB_SERVICE_PACKAGE = "gen.sub.service.package";
    String PROP_GEN_SUB_MODEL_PACKAGE = "gen.sub.model.package";

    String PROP_GEN_JDBC_DRIVER = "gen.jdbc.driver";
    String PROP_GEN_JDBC_URL = "gen.jdbc.url";
    String PROP_GEN_JDBC_USERNAME = "gen.jdbc.username";
    String PROP_GEN_JDBC_PASSWORD = "gen.jdbc.password";
    String GEN_TABLE_DIR_RULE = "gen.table.dir.rule";
    String GEN_TABLE_DIR_REPLACE = "gen.table.dir.replace";
    String GEN_TABLE_COLUMN_RULE = "gen.table.column.rule";
    String GEN_TABLE_COLUMN_REPLACE = "gen.table.column.replace";

    String DIR_BASE_RESOURCES = "src/main/resources/";
    String DIR_BASE_JAVA = "src/main/java/";
}
