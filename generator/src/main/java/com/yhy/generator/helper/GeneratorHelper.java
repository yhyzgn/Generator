package com.yhy.generator.helper;

import com.yhy.generator.api.loader.TableLoader;
import com.yhy.generator.core.file.JavaFile;
import com.yhy.generator.generator.MapperGenerator;
import com.yhy.generator.generator.ModelGenerator;
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

        ModelGenerator modelGenerator = new ModelGenerator(table);
        JavaFile model = modelGenerator.generate();
        LOGGER.info("\n=================================================\n" + model + "\n=================================================");

        MapperGenerator mapperGenerator = new MapperGenerator(table, modelGenerator.getReferenceModel());
        JavaFile mapper = mapperGenerator.generate();
        LOGGER.info("\n=================================================\n" + mapper + "\n=================================================");

//        MapperXmlGenerator xmlGenerator = new MapperXmlGenerator(table);
//        xmlGenerator.generate();
    }
}
