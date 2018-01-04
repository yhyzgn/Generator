package com.yhy.generator.init;

import com.yhy.generator.common.Const;
import com.yhy.generator.utils.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 13:16
 * version: 1.0.0
 * desc   : 初始化监听器，需要在web.xml中配置
 */
@WebListener
public class InitializerListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String param = sce.getServletContext().getInitParameter(Const.INITIALIZER_PROPERTIES);
        if (null == param) {
            throw new IllegalArgumentException("Must set configuration for the param 'genConfig' as configuration properties if use " + getClass().getSimpleName());
        }
        LOGGER.info("The initializer param 'genConfig' is " + param);
        PropUtils.load(Const.INITIALIZER_PROPERTIES, param);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
