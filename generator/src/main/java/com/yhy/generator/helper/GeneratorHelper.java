package com.yhy.generator.helper;

import com.yhy.generator.api.loader.TableLoader;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.generator.MapperGenerator;
import com.yhy.generator.generator.ModelGenerator;
import com.yhy.generator.generator.ServiceGenerator;
import com.yhy.generator.generator.ServiceImplGenerator;
import com.yhy.generator.model.table.Table;
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
//        ModelGenerator modelGenerator = new ModelGenerator(table);
//        modelGenerator.generate();

//        MapperGenerator mapperGenerator = new MapperGenerator(table);
//        mapperGenerator.generate();

//        ServiceGenerator serviceGenerator = new ServiceGenerator(table);
//        serviceGenerator.generate();

//        ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator(table);
//        serviceImplGenerator.generate();

        ModelGenerator modelGenerator = new ModelGenerator(table);
        JavaFile model = modelGenerator.generate(new FileWriter.OnWriteListener() {
            @Override
            public void onFinish() {
                System.out.println("Model 生成完成！");
                MapperGenerator mapperGenerator = new MapperGenerator(table);
                JavaFile mapper = mapperGenerator.generate(new FileWriter.OnWriteListener() {
                    @Override
                    public void onFinish() {
                        System.out.println("Mapper 生成完成！");
                        ServiceGenerator serviceGenerator = new ServiceGenerator(table);
                        JavaFile service = serviceGenerator.generate(new FileWriter.OnWriteListener() {
                            @Override
                            public void onFinish() {
                                System.out.println("Service 生成完成！");
                                ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator(table);
                                JavaFile serviceImpl = serviceImplGenerator.generate();
                                LOGGER.info("\n=================================================\n" + serviceImpl + "\n=================================================");
                            }
                        });
                        LOGGER.info("\n=================================================\n" + service + "\n=================================================");
                    }
                });
                LOGGER.info("\n=================================================\n" + mapper + "\n=================================================");
            }
        });
        LOGGER.info("\n=================================================\n" + model + "\n=================================================");
    }
}
