package com.yhy.generator.core.java.type;

import com.yhy.generator.core.java.type.abs.AbsSpec;
import com.yhy.generator.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-29 11:39
 * version: 1.0.0
 * desc   :
 */
public class StMentSpec implements AbsSpec {
    private static final Pattern PATTERN = Pattern.compile("(\\$[T,I,S])");
    private String pattStMent;
    private Object[] args;
    private String statement;
    private List<Class<?>> curClassList;
    private List<StMentSpec> stMentSpecList;

    public StMentSpec() {
        this(null);
    }

    public StMentSpec(String pattStMent) {
        this.pattStMent = pattStMent;
        this.statement = pattStMent;
        curClassList = new ArrayList<>();
        stMentSpecList = new ArrayList<>();
    }

    public StMentSpec format(Object... args) {
        return format(pattStMent, args);
    }

    public StMentSpec format(String pattStMent, Object... args) {
        this.pattStMent = pattStMent;
        this.args = args;
        return format();
    }

    private StMentSpec format() {
        if (StringUtils.isNotEmpty(pattStMent) && check()) {
            Matcher matcher = PATTERN.matcher(pattStMent);
            StringBuffer sb = new StringBuffer();
            String type;
            int index = 0;
            Class<?> clazz;
            while (matcher.find()) {
                type = matcher.group(1);
                switch (type) {
                    case "$T":
                        clazz = (Class<?>) args[index++];
                        curClassList.add(clazz);
                        matcher.appendReplacement(sb, clazz.getSimpleName());
                        break;
                    case "$I":
                        matcher.appendReplacement(sb, args[index++] + "");
                        break;
                    case "$S":
                        matcher.appendReplacement(sb, "\"" + args[index++] + "\"");
                        break;
                }
            }
            matcher.appendTail(sb);
            statement = sb.toString();
        }
        return this;
    }

    private boolean check() {
        if (StringUtils.isEmpty(pattStMent)) {
            throw new IllegalArgumentException("The argument 'pattStMent' of constructor can not be null.");
        }

        int typeCount = 0;
        String temp = PATTERN.matcher(pattStMent).replaceAll("\\$");
        for (char ch : temp.toCharArray()) {
            if (ch == '$') {
                typeCount++;
            }
        }
        if (typeCount > 0) {
            if (null == args) {
                throw new IllegalArgumentException("The argument 'args' of constructor can not be null.");
            }
            if (typeCount != args.length) {
                throw new IllegalArgumentException("The count of variable in 'pattStMent' is : " + typeCount + ", but the count of 'args' is : " + args.length + ".");
            }
        }
        return true;
    }

    public List<StMentSpec> getStMentSpecList() {
        return stMentSpecList;
    }

    public StMentSpec setStMentSpecList(List<StMentSpec> stMentSpecList) {
        this.stMentSpecList = stMentSpecList;
        return this;
    }

    public StMentSpec addStMentSpec(StMentSpec stMentSpec) {
        stMentSpecList.add(stMentSpec);
        return this;
    }

    @Override
    public List<Class<?>> getClassList() {
        return getAllClassList(this);
    }

    @Override
    public String string(String indent) {
        return getString(this, indent);
    }

    private List<Class<?>> getAllClassList(StMentSpec stMentSpec) {
        List<Class<?>> result = new ArrayList<>();
        if (null != stMentSpec.getStMentSpecList()) {
            for (StMentSpec st : stMentSpec.getStMentSpecList()) {
                result.addAll(getAllClassList(st));
            }
        }
        if (null != stMentSpec.curClassList) {
            result.addAll(stMentSpec.curClassList);
        }
        return result;
    }

    private String getString(StMentSpec stMentSpec, String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(indent).append(stMentSpec.statement);
        if (null != stMentSpec.getStMentSpecList() && !stMentSpec.getStMentSpecList().isEmpty()) {
            sb.append(" {").append(System.getProperty("line.separator"));
            for (StMentSpec st : stMentSpec.getStMentSpecList()) {
                sb.append(getString(st, indent + "\t"));
            }
            sb.append(indent).append("}");
        } else {
            sb.append(";");
        }
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }
}
