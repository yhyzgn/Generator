package com.yhy.generator.simple.test;

import com.yhy.generator.api.db.TableApi;
import com.yhy.generator.common.Const;
import com.yhy.generator.utils.ConvertUtils;
import com.yhy.generator.utils.PropUtils;
import com.yhy.generator.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 14:07
 * version: 1.0.0
 * desc   :
 */
public class AppTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

    @Test
    void test() {
        String test = "generator-config_fileTest";
        String result = ConvertUtils.line2Hump(test);
        LOGGER.info(test + " 下划线转驼峰结果：" + result);
    }

    @Test
    void stringTest() {
        boolean empty = StringUtils.equals("", "");
        LOGGER.info(empty + "");
    }

    @Test
    void dbNameTest() {
//        String dbUrl = "jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
//        Pattern pattern = Pattern.compile("/(\\w+)\\?");

        String dbUrl = "jdbc:mysql://localhost:3306/ssm";
        Pattern pattern = Pattern.compile(".*/(\\w+)$");
        Matcher matcher = pattern.matcher(dbUrl);
        LOGGER.info(matcher.find() + " " + matcher.group(1));
    }

    @Test
    void pathTest() {
        String path = System.getProperty("user.dir");
        if (StringUtils.isEmpty(path)) {
            path = "/";
        }
        path = path.replaceAll("\\\\", "/");
        LOGGER.info(path);
    }

    @Test
    void separatorTest(){
        String result = System.getProperty("line.separator");
    }
}
