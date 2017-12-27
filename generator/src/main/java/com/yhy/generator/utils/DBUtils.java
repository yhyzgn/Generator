package com.yhy.generator.utils;

import com.yhy.generator.model.table.Column;

import java.sql.Date;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 13:49
 * version: 1.0.0
 * desc   :
 */
@SuppressWarnings("ALL")
public class DBUtils {
    private DBUtils() {
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
        }
        return column;
    }
}
