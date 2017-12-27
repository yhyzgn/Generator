package com.yhy.generator.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 16:55
 * version: 1.0.0
 * desc   :
 */
public class DBHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);

    private Connection mConn;
    private PreparedStatement mStatement;
    private ResultSet mResult;

    public DBHelper(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
            mConn = DriverManager.getConnection(url, username, password);
            LOGGER.info("Connect database success !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> select(String sql, Object... params) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();

        mStatement = mConn.prepareStatement(sql);
        if (null != params && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                mStatement.setObject(i + 1, params[i]);
            }
        }
        mResult = mStatement.executeQuery();
        ResultSetMetaData metaData = mResult.getMetaData();
        int clnCount = metaData.getColumnCount();
        Map<String, Object> temp;
        String clnName;
        Object clnValue;
        while (mResult.next()) {
            temp = new HashMap<>();

            for (int i = 1; i <= clnCount; i++) {
                clnName = metaData.getColumnName(i);
                clnValue = mResult.getObject(clnName);
                if (null == clnValue) {
                    clnValue = "";
                }
                temp.put(clnName, clnValue);
            }
            result.add(temp);
        }
        return result;
    }

    public void release() {
        try {
            if (null != mResult) {
                mResult.close();
            }
            if (null != mStatement) {
                mStatement.close();
            }
            if (null != mConn) {
                mConn.close();
            }
            LOGGER.info("The connection of database was closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
