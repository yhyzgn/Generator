package com.yhy.generator.helper;

import com.yhy.generator.api.loader.TableLoader;
import com.yhy.generator.common.Const;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.core.file.XmlFile;
import com.yhy.generator.generator.*;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 17:18
 * version: 1.0.0
 * desc   :
 */
public class GeneratorHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorHelper.class);

    public List<Table> allTableList() {
        return new TableLoader().allTableList();
    }

    public void generate(Table table) {
        if (null == table) {
            return;
        }

        boolean enable = PropUtils.getBoolean(Const.INITIALIZER_PROPERTIES, Const.PROP_GEN_ENABLE, false);
        if (!enable) {
            LOGGER.error("If you want to generate code, set 'gen.enable' as 'true' in properties file at first, please !");
            return;
        }

        ModelGenerator modelGenerator = new ModelGenerator(table);
        modelGenerator.generate(new FileWriter.OnWriteListener<JavaFile>() {
            @Override
            public void onFinish(JavaFile model) {
                System.out.println("Model 生成完成！");
                LOGGER.info("\n=================================================\n" + model + "\n=================================================");

                MapperGenerator mapperGenerator = new MapperGenerator(table);
                mapperGenerator.generate(new FileWriter.OnWriteListener<JavaFile>() {
                    @Override
                    public void onFinish(JavaFile mapper) {
                        System.out.println("Mapper 生成完成！");
                        LOGGER.info("\n=================================================\n" + mapper + "\n=================================================");

                        MapperXmlGenerator mapperXmlGenerator = new MapperXmlGenerator(table);
                        mapperXmlGenerator.generate(new FileWriter.OnWriteListener<XmlFile>() {
                            @Override
                            public void onFinish(XmlFile mapperXml) {
                                System.out.println("Mapper xml 生成完成！");
                                LOGGER.info("\n=================================================\n" + mapperXml + "\n=================================================");
                            }
                        });

                        ServiceGenerator serviceGenerator = new ServiceGenerator(table);
                        serviceGenerator.generate(new FileWriter.OnWriteListener<JavaFile>() {
                            @Override
                            public void onFinish(JavaFile service) {
                                System.out.println("Service 生成完成！");
                                LOGGER.info("\n=================================================\n" + service + "\n=================================================");

                                ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator(table);
                                JavaFile serviceImpl = serviceImplGenerator.generate();
                                LOGGER.info("\n=================================================\n" + serviceImpl + "\n=================================================");
                            }
                        });
                    }
                });
            }
        });
    }
}
