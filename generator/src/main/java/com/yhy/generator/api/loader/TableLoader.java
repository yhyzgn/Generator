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
 * desc   :
 */
public class TableLoader {

    public List<Table> allTableList() {
        TableApi api = new TableApi();

        List<TableInfo> infoList = api.loadTableInfoList();
        if (null == infoList) {
            return null;
        }
        List<Table> tableList = new ArrayList<>();
        Table table;
        List<Column> columnList;
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
