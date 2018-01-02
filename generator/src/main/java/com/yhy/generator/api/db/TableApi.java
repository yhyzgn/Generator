package com.yhy.generator.api.db;

import com.yhy.generator.api.db.base.BaseDBApi;
import com.yhy.generator.helper.DBHelper;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.TableInfo;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.GenUtils;
import com.yhy.generator.utils.DateUtils;
import com.yhy.generator.utils.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 17:49
 * version: 1.0.0
 * desc   :
 */
public class TableApi extends BaseDBApi {

    public List<TableInfo> loadTableInfoList() {
        DBHelper helper = new DBHelper(getDriver(), getUrl(), getUsername(), getPassword());
        try {
            List<Map<String, Object>> result = helper.select("SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME <> ?", getDbName(), "generator");
            if (null != result && !result.isEmpty()) {
                List<TableInfo> tableInfoList = new ArrayList<>();
                TableInfo tableInfo;
                for (Map<String, Object> item : result) {
                    tableInfo = new TableInfo();
                    tableInfo.setCatalog(String.valueOf(item.get("TABLE_CATALOG")));
                    tableInfo.setSchema(String.valueOf(item.get("TABLE_SCHEMA")));
                    tableInfo.setName(String.valueOf(item.get("TABLE_NAME")));
                    tableInfo.setType(String.valueOf(item.get("TABLE_TYPE")));
                    tableInfo.setEngine(String.valueOf(item.get("ENGINE")));
                    tableInfo.setVersion(Long.valueOf(String.valueOf(item.get("VERSION"))));
                    tableInfo.setRowFormat(String.valueOf(item.get("ROW_FORMAT")));
                    tableInfo.setCreateTime(DateUtils.getTime(item.get("CREATE_TIME")));
                    tableInfo.setUpdateTime(DateUtils.getTime(item.get("UPDATE_TIME")));
                    tableInfo.setCollation(String.valueOf(item.get("TABLE_COLLATION")));
                    tableInfo.setComment(String.valueOf(item.get("TABLE_COMMENT")));
                    tableInfoList.add(tableInfo);
                }
                return tableInfoList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.release();
        }
        return null;
    }

    public List<Column> getColumnList(String name) {
        DBHelper helper = new DBHelper(getDriver(), getUrl(), getUsername(), getPassword());
        try {
            List<Map<String, Object>> result = helper.select("SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?", getDbName(), name);
            if (null != result && !result.isEmpty()) {
                List<Column> columnList = new ArrayList<>();
                Column column;
                for (Map<String, Object> item : result) {
                    column = new Column();
                    column.setTableCatalog(String.valueOf(item.get("TABLE_CATALOG")));
                    column.setTableName(String.valueOf(item.get("TABLE_NAME")));
                    column.setTableSchema(String.valueOf(item.get("TABLE_SCHEMA")));
                    column.setNullable(StringUtils.equals("YES", String.valueOf(item.get("IS_NULLABLE"))));
                    column.setExtra(String.valueOf(item.get("EXTRA")));
                    column.setRealName(String.valueOf(item.get("COLUMN_NAME")));
                    column.setName(ConvertUtils.line2Hump(column.getRealName()));
                    column.setPosition(Integer.valueOf(String.valueOf(item.get("ORDINAL_POSITION"))));
                    column.setDataType(String.valueOf(item.get("DATA_TYPE")));
                    column.setComment(String.valueOf(item.get("COLUMN_COMMENT")));
                    GenUtils.setKey(column, item.get("COLUMN_KEY"));
                    GenUtils.setDefValue(column, item.get("COLUMN_DEFAULT"));
                    columnList.add(column);
                }
                return columnList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.release();
        }
        return null;
    }
}
