package com.yhy.generator.common;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 13:50
 * version: 1.0.0
 * desc   : 全局变量
 */
public interface Const {
    // 配置文件对应PropUtils中的名称，需要在web.xml中配置
    String INITIALIZER_PROPERTIES = "genConfig";

    // 生成代码功能开关
    String PROP_GEN_ENABLE = "gen.enable";

    //Web项目根目录（如果是多模块，就设置为主模块根目录）
    String PROP_GEN_PROJECT_ROOT = "gen.project.root";

    // JDBC配置
    String PROP_GEN_JDBC_DRIVER = "gen.jdbc.driver";
    String PROP_GEN_JDBC_URL = "gen.jdbc.url";
    String PROP_GEN_JDBC_USERNAME = "gen.jdbc.username";
    String PROP_GEN_JDBC_PASSWORD = "gen.jdbc.password";

    // 生成代码规则配置
    // XxxMapper.xml文件目录，如：classpath:mapper/
    String PROP_GEN_SUB_DIR_MAPPER_XML = "gen.sub.dir.mapper.xml";
    // XxxMapper.java (Mapper)包路径
    String PROP_GEN_SUB_PACKAGE_MAPPER = "gen.sub.package.mapper";
    // XxxService.java (Service)包路径
    String PROP_GEN_SUB_PACKAGE_SERVICE = "gen.sub.package.service";
    // xx.java (Model)包路径
    String PROP_GEN_SUB_PACKAGE_MODEL = "gen.sub.package.model";
    // 按表名生成子目录时的命名规则，如：表明为ts_user，生成的子目录名为user
    String GEN_TABLE_DIR_RULE = "gen.table.dir.rule";
    String GEN_TABLE_DIR_REPLACE = "gen.table.dir.replace";
    // 按表名生成Model时的命名规则，如：表明为ts_user，生成的Model类名为User
    String GEN_TABLE_FIELD_RULE = "gen.table.field.rule";
    String GEN_TABLE_FIELD_REPLACE = "gen.table.field.replace";

    // 生成代码时，注释中的作者信息
    String GEN_AUTHOR_NAME = "gen.author.name";
    String GEN_AUTHOR_EMAIL = "gen.author.email";

    // 各种文件的路径，无需任何配置
    String DIR_BASE_RESOURCES = "src/main/resources/";
    String DIR_BASE_JAVA = "src/main/java/";
}
