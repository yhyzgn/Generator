package com.yhy.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 14:01
 * version: 1.0.0
 * desc   :
 */
public class ConvertUtils {

    private ConvertUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static String line2Hump(String line) {
        Pattern linePattern = Pattern.compile("(-|_)(\\w)");
        Matcher matcher = linePattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(2).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
