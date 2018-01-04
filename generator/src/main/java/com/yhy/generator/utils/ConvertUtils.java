package com.yhy.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 14:01
 * version: 1.0.0
 * desc   : 转换工具
 */
public class ConvertUtils {

    private ConvertUtils() {
        throw new UnsupportedOperationException("Can not instantiate utils class.");
    }

    public static String line2Hump(String line) {
        if (StringUtils.isEmpty(line)) {
            return line;
        }
        Pattern linePattern = Pattern.compile("(-|_)(\\w)");
        Matcher matcher = linePattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(2).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String caseFirstCharUpper(String original) {
        if (StringUtils.isEmpty(original)) {
            return original;
        }
        Pattern linePattern = Pattern.compile("^(\\w)");
        Matcher matcher = linePattern.matcher(original);
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String caseFirstCharLower(String original) {
        if (StringUtils.isEmpty(original)) {
            return original;
        }
        Pattern linePattern = Pattern.compile("^(\\w)");
        Matcher matcher = linePattern.matcher(original);
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
