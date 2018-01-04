package com.yhy.generator.core.java.type.abs;

import com.yhy.generator.core.java.Clazz;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-29 11:19
 * version: 1.0.0
 * desc   : 各种类型的基类
 */
public interface AbsSpec {

    /**
     * 获取所有用到的类
     *
     * @return 所有用到的类
     */
    List<Clazz> getClassList();

    /**
     * 字符串序列化当前模块
     *
     * @param indent 缩进
     * @return 序列化后的模块
     */
    String string(String indent);
}
