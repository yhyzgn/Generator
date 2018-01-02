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

    String PROP_GEN_SUB_DIR_MAPPER_XML = "gen.sub.dir.mapper.xml";
    String PROP_GEN_SUB_PACKAGE_MAPPER = "gen.sub.package.mapper";
    String PROP_GEN_SUB_PACKAGE_SERVICE = "gen.sub.package.service";
    String PROP_GEN_SUB_PACKAGE_MODEL = "gen.sub.package.model";

    String PROP_GEN_JDBC_DRIVER = "gen.jdbc.driver";
    String PROP_GEN_JDBC_URL = "gen.jdbc.url";
    String PROP_GEN_JDBC_USERNAME = "gen.jdbc.username";
    String PROP_GEN_JDBC_PASSWORD = "gen.jdbc.password";
    String GEN_TABLE_DIR_RULE = "gen.table.dir.rule";
    String GEN_TABLE_DIR_REPLACE = "gen.table.dir.replace";
    String GEN_TABLE_FIELD_RULE = "gen.table.field.rule";
    String GEN_TABLE_FIELD_REPLACE = "gen.table.field.replace";

    String GEN_AUTHOR_NAME = "gen.author.name";
    String GEN_AUTHOR_EMAIL = "gen.author.email";

    String DIR_BASE_RESOURCES = "src/main/resources/";
    String DIR_BASE_JAVA = "src/main/java/";
}
