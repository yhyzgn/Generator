package com.yhy.generator.api.loader;

import com.yhy.generator.api.db.GenApi;
import com.yhy.generator.model.GenRecord;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-02 17:59
 * version: 1.0.0
 * desc   :
 */
public class GenLoader {

    public int save(GenRecord record) {
        GenApi genApi = new GenApi();
        return genApi.save(record);
    }
}
