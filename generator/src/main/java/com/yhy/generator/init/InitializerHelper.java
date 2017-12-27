package com.yhy.generator.init;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 13:45
 * version: 1.0.0
 * desc   :
 */
public class InitializerHelper {
    private volatile static InitializerHelper instance;

    private String root;

    private boolean enable;

    private InitializerHelper() {
        if (null != instance) {
            throw new UnsupportedOperationException("Can not instantiate singleton class.");
        }
    }

    public static InitializerHelper getInstance() {
        if (null == instance) {
            synchronized (InitializerHelper.class) {
                if (null == instance) {
                    instance = new InitializerHelper();
                }
            }
        }
        return instance;
    }

    public String getRoot() {
        return root;
    }

    public InitializerHelper setRoot(String root) {
        this.root = root;
        return this;
    }

    public boolean getEnable() {
        return enable;
    }

    public InitializerHelper setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }
}
