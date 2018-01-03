package com.yhy.generator.api.loader;

import com.yhy.generator.api.db.GenApi;
import com.yhy.generator.model.GenRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-02 17:59
 * version: 1.0.0
 * desc   :
 */
public class GenLoader {

    private volatile static GenLoader instance;

    private Map<String, GenRecord> cache;

    private GenApi api;

    private GenLoader() {
        if (null != instance) {
            throw new UnsupportedOperationException("Can not instantiate singleton class.");
        }
        cache = new HashMap<>();
        api = new GenApi();
    }

    public static GenLoader getInstance() {
        if (null == instance) {
            synchronized (GenLoader.class) {
                instance = new GenLoader();
            }
        }
        return instance;
    }

    public GenRecord get(String tableName) {
        if (cache.containsKey(tableName)) {
            return cache.get(tableName);
        }
        GenRecord record = api.get(tableName);
        cache.put(tableName, record);
        return record;
    }

    public int save(GenRecord record) {
        cache.put(record.getTable(), record);
        return api.save(record);
    }
}
