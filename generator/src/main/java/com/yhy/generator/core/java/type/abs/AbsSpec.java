package com.yhy.generator.core.java.type.abs;

import com.yhy.generator.core.java.Clazz;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-29 11:19
 * version: 1.0.0
 * desc   :
 */
public interface AbsSpec {

    List<Clazz> getClassList();

    String string(String indent);
}
