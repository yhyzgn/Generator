package com.yhy.generator.generator;

import com.yhy.generator.core.xml.XmlFile;
import com.yhy.generator.generator.base.Generator;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 15:04
 * version: 1.0.0
 * desc   :
 */
public class MapperXmlGenerator extends Generator<XmlFile> {

    public MapperXmlGenerator(String tableName) {
        super(tableName);
    }

    @Override
    protected FileType fileType() {
        return FileType.MAPPER_XML;
    }

    @Override
    public void generate(XmlFile file) {
        String dir = getPath();
        LOGGER.info("\n=================================================\n" + dir + "\n=================================================");
    }
}
