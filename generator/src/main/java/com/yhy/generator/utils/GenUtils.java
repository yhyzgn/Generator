package com.yhy.generator.utils;

import com.yhy.generator.common.Const;
import com.yhy.generator.core.java.Scope;
import com.yhy.generator.core.java.type.DocSpec;
import com.yhy.generator.core.java.type.TypeSpec;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 13:49
 * version: 1.0.0
 * desc   :
 */
@SuppressWarnings("ALL")
public class GenUtils {
    private GenUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static Column setKey(Column column, Object key) {
        if (null == column || null == key) {
            return column;
        }

        String strKey = String.valueOf(key);
        if (StringUtils.equals("PRI", strKey)) {
            column.setPrimary(true);
            column.setForeign(false);
        } else if (StringUtils.equals("MUL", strKey)) {
            column.setPrimary(false);
            column.setForeign(true);
        }
        return column;
    }

    public static Column setDefValue(Column column, Object defValue) {
        if (null == column) {
            return null;
        }
        String dataType = column.getDataType();
        if (StringUtils.isEmpty(dataType)) {
            dataType = "varchar";
        }

        String strDef = String.valueOf(defValue);
        switch (dataType) {
            case "int":
            case "tinyint":
            case "smallint":
            case "mediumint":
            case "integer":
                if (StringUtils.isEmpty(strDef)) {
                    strDef = "0";
                }
                column.setDefValue(Integer.valueOf(strDef));
                break;
            case "bigint":
                if (StringUtils.isEmpty(strDef)) {
                    strDef = "0";
                }
                column.setDefValue(Long.valueOf(strDef));
                break;
            case "char":
            case "varchar":
            case "text":
            case "mediumtext":
            case "longtext":
                column.setDefValue(strDef);
                break;
            case "datetime":
            case "date":
            case "time":
                if (StringUtils.isNotEmpty(strDef)) {
                    column.setDefValue(Date.valueOf(strDef).getTime());
                } else {
                    column.setDefValue(Long.valueOf(0));
                }
                break;
            case "timestamp":
                column.setDefValue(DateUtils.getTime(defValue));
                break;
            case "decimal":
                column.setDefValue(new BigDecimal(defValue.toString()));
                break;
        }
        return column;
    }

    public static Class<?> mapColumnType(Column column) {
        if (null == column) {
            return null;
        }
        String dataType = column.getDataType();
        if (StringUtils.isEmpty(dataType)) {
            dataType = "varchar";
        }
        switch (dataType) {
            case "int":
            case "tinyint":
            case "smallint":
            case "mediumint":
            case "integer":
                return Integer.class;
            case "char":
            case "varchar":
            case "text":
            case "mediumtext":
            case "longtext":
                return String.class;
            case "bigint":
            case "datetime":
            case "date":
            case "time":
            case "timestamp":
                return Long.class;
            case "decimal":
                return BigDecimal.class;
        }
        return String.class;
    }

    public static Column normalizeFieldName(Column column) {
        String rule = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_TABLE_FIELD_RULE);
        String replace = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_TABLE_FIELD_REPLACE);
        if (StringUtils.isEmpty(rule)) {
            rule = "^_";
        }
        if (null == replace) {
            replace = "";
        }
        column.setName(ConvertUtils.line2Hump(column.getRealName().replaceAll(rule, replace).toLowerCase(Locale.getDefault())));
        return column;
    }

    public static void genClassDoc(Table table, TypeSpec type) {
        type.addDocSpec(new DocSpec("author :", PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_AUTHOR_NAME, "颜洪毅")));
        type.addDocSpec(new DocSpec("e-mail :", PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.GEN_AUTHOR_EMAIL, "yhyzgn@gmail.com")));
        type.addDocSpec(new DocSpec("time   :", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())));
        type.addDocSpec(new DocSpec("version:", "1.0.0"));
        type.addDocSpec(new DocSpec("desc   :", table.getInfo().getComment()));
    }
}
