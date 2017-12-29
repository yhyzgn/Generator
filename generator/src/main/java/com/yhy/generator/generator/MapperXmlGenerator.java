package com.yhy.generator.generator;

import com.yhy.generator.core.file.XmlFile;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.helper.FileWriter;
import com.yhy.generator.model.table.Table;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 15:04
 * version: 1.0.0
 * desc   :
 */
public class MapperXmlGenerator extends Generator<XmlFile> {

    public MapperXmlGenerator(Table table) {
        super(table);
    }

    @Override
    protected FileType fileType() {
        return FileType.MAPPER_XML;
    }

    @Override
    public XmlFile getDataFile() {
        return genXml();
    }

    private XmlFile genXml() {
        XmlFile xmlFile = new XmlFile();
        return xmlFile;
    }
}
