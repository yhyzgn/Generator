package com.yhy.generator.api.db.base;

import com.yhy.generator.common.Const;
import com.yhy.generator.utils.PropUtils;
import com.yhy.generator.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 17:52
 * version: 1.0.0
 * desc   :
 */
public class BaseDBApi {
    private String driver;
    private String url;
    private String username;
    private String password;

    private String dbName;

    public BaseDBApi() {
        driver = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_JDBC_DRIVER);
        url = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_JDBC_URL);
        username = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_JDBC_USERNAME);
        password = PropUtils.get(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_JDBC_PASSWORD);

        subDbName();
    }

    private void subDbName() {
        if (StringUtils.isEmpty(url)) {
            return;
        }

        Pattern pattern;
        if (url.contains("?")) {
            pattern = Pattern.compile("/(\\w+)\\?");
        } else {
            pattern = Pattern.compile(".*/(\\w+)$");
        }
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            dbName = matcher.group(1);
        }
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }
}
