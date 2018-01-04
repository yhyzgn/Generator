package com.yhy.generator.api.loader;

import com.yhy.generator.api.db.TableApi;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.model.table.TableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 14:39
 * version: 1.0.0
 * desc   : 系统表相关加载器
 */
public class TableLoader {

    /**
     * 获取所有的表信息
     *
     * @return 所有表信息
     */
    public List<Table> allTableList() {
        TableApi api = new TableApi();

        // 先获取到所有表的基本信息
        List<TableInfo> infoList = api.loadTableInfoList();
        if (null == infoList) {
            return null;
        }
        List<Table> tableList = new ArrayList<>();
        Table table;
        List<Column> columnList;

        // 再逐一获取每个表的所有字段信息
        for (TableInfo info : infoList) {
            table = new Table();
            table.setInfo(info);
            columnList = api.getColumnList(info.getName());
            if (null != columnList) {
                for (Column column : columnList) {
                    if (column.getPrimary()) {
                        table.setPrimary(column);
                    }
                }
            }
            table.setColumnList(columnList);
            tableList.add(table);
        }
        return tableList;
    }
}
