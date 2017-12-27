package com.yhy.generator.helper;

import com.yhy.generator.api.loader.TableLoader;
import com.yhy.generator.core.xml.Attribute;
import com.yhy.generator.core.xml.Element;
import com.yhy.generator.core.xml.XmlFile;
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

    public List<Table> getTableList() {
        return new TableLoader().load();
    }

    public void generate(Table table) {
        if (null == table) {
            return;
        }

        XmlFile xmlFile = new XmlFile();

        Element element = new Element("root");
        element.setContent("This is content !");

        element.addAttribute(new Attribute("path", "/home/yhy/xxx/"));
        element.addAttribute(new Attribute("version", "1.0"));

        xmlFile.addElement(element);
        LOGGER.info("\n=================================================\n" + xmlFile.toString() + "\n=================================================");
    }
}
