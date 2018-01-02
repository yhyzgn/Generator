package com.yhy.generator.model.table;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 14:37
 * version: 1.0.0
 * desc   :
 */
public class Table {
    private TableInfo info;
    private List<Column> columnList;
    private Column primary;

    public TableInfo getInfo() {
        return info;
    }

    public void setInfo(TableInfo info) {
        this.info = info;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public Column getPrimary() {
        return primary;
    }

    public void setPrimary(Column primary) {
        this.primary = primary;
    }
}
