package com.yhy.generator.api.db;

import com.yhy.generator.api.db.base.BaseDBApi;
import com.yhy.generator.helper.DBHelper;
import com.yhy.generator.model.GenRecord;
import com.yhy.generator.model.table.Table;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-02 17:39
 * version: 1.0.0
 * desc   : 生成代码相关API
 */
public class GenApi extends BaseDBApi {

    /**
     * 获取代码生成记录
     *
     * @param tableName 表名
     * @return 生成记录
     */
    public GenRecord get(String tableName) {
        DBHelper helper = new DBHelper(getDriver(), getUrl(), getUsername(), getPassword());
        try {
            List<Map<String, Object>> result = helper.select("SELECT * FROM generator WHERE gen_table = ?", tableName);
            if (null != result && !result.isEmpty()) {
                Map<String, Object> genResult = result.get(0);
                GenRecord record = new GenRecord();
                record.setTable(tableName);
                record.setModel(String.valueOf(genResult.get("gen_model")));
                record.setMapper(String.valueOf(genResult.get("gen_mapper")));
                record.setMapperXml(String.valueOf(genResult.get("gen_mapper_xml")));
                record.setService(String.valueOf(genResult.get("gen_service")));
                record.setServiceImpl(String.valueOf(genResult.get("gen_service_impl")));
                return record;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.release();
        }
        return null;
    }

    /**
     * 保存代码生成记录
     *
     * @param record 生成记录
     * @return 影响行数
     */
    public int save(GenRecord record) {
        DBHelper helper = new DBHelper(getDriver(), getUrl(), getUsername(), getPassword());
        try {
            List<Map<String, Object>> result = helper.select("SELECT * FROM generator WHERE gen_table = ?", record.getTable());
            if (null != result && !result.isEmpty()) {
                // update
                return helper.update("UPDATE generator SET gen_model = ?, gen_mapper = ?, gen_mapper_xml = ?, gen_service = ?, gen_service_impl = ? WHERE gen_table = ?", record.getModel(), record.getMapper(), record.getMapperXml(), record.getService(), record.getServiceImpl(), record.getTable());
            } else {
                // insert
                return helper.update("INSERT INTO generator VALUES(?, ?, ?, ?, ?, ?)", record.getTable(), record.getModel(), record.getMapper(), record.getMapperXml(), record.getService(), record.getServiceImpl());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.release();
        }
        return 0;
    }
}
