package com.yhy.generator.api.db;

import com.yhy.generator.api.db.base.BaseDBApi;
import com.yhy.generator.helper.DBHelper;
import com.yhy.generator.model.GenRecord;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-02 17:39
 * version: 1.0.0
 * desc   :
 */
public class GenApi extends BaseDBApi {

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
        }
        return 0;
    }
}
