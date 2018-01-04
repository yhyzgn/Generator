package com.yhy.generator.generator;

import com.yhy.generator.api.loader.GenLoader;
import com.yhy.generator.core.file.XmlFile;
import com.yhy.generator.core.xml.Attribute;
import com.yhy.generator.core.xml.Element;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.helper.FileWriter;
import com.yhy.generator.model.GenRecord;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.utils.GenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 15:04
 * version: 1.0.0
 * desc   : Mapper xml 生成器
 */
public class MapperXmlGenerator extends Generator<XmlFile> {
    private GenRecord genRecord;

    public MapperXmlGenerator(Table table) {
        super(table);
        // 根据表名获取到已生成的记录
        genRecord = GenLoader.getInstance().get(table.getInfo().getName());
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

        // mapper根节点
        Element mapper = new Element("mapper");
        mapper.addAttribute(new Attribute("namespace", genRecord.getMapper()));

        // resultMap节点
        Element resultMap = new Element("resultMap");
        resultMap.addAttribute(new Attribute("id", "BaseResultMap"));
        resultMap.addAttribute(new Attribute("type", genRecord.getModel()));
        Element result;
        for (Column column : table.getColumnList()) {
            result = new Element("result");
            result.addAttribute(new Attribute("column", column.getRealName()));
            result.addAttribute(new Attribute("jdbcType", GenUtils.jdbcType(column)));
            result.addAttribute(new Attribute("property", column.getName()));
            resultMap.addElement(result);
        }

        // insert节点
        Element insert = new Element("insert");
        insert.addAttribute(new Attribute("id", "insert"));
        insert.addAttribute(new Attribute("parameterType", genRecord.getModel()));
        StringBuilder sbInsertColumn = new StringBuilder();
        StringBuilder sbInsertValue = new StringBuilder();
        for (int i = 0; i < table.getColumnList().size(); i++) {
            sbInsertColumn.append(table.getColumnList().get(i).getRealName());
            sbInsertValue.append("#{").append(table.getColumnList().get(i).getName()).append(", jdbcType = ").append(GenUtils.jdbcType(table.getColumnList().get(i))).append("}");
            if (i < table.getColumnList().size() - 1) {
                sbInsertColumn.append(", ");
                sbInsertValue.append(", ");
            }
        }
        insert.setContent("INSERT INTO " + table.getInfo().getName() + "(" + sbInsertColumn + ")" + " VALUES (" + sbInsertValue + ")");

        // selectById节点
        Element selectById = null;
        if (null != table.getPrimary()) {
            selectById = new Element("select");
            selectById.addAttribute(new Attribute("id", "selectById"));
            selectById.addAttribute(new Attribute("parameterType", GenUtils.mapColumnType(table.getPrimary()).getName()));
            selectById.addAttribute(new Attribute("resultMap", "BaseResultMap"));
            selectById.setContent("SELECT * FROM " + table.getInfo().getName() + " WHERE " + table.getPrimary().getRealName() + " = #{" + table.getPrimary().getName() + ", jdbcType = " + GenUtils.jdbcType(table.getPrimary()) + "}");
        }

        // selectAll节点
        Element selectAll = new Element("select");
        selectAll.addAttribute(new Attribute("id", "selectAll"));
        selectAll.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        selectAll.setContent("SELECT * FROM " + table.getInfo().getName());

        // update节点
        Element update = new Element("update");
        update.addAttribute(new Attribute("id", "update"));
        update.addAttribute(new Attribute("parameterType", genRecord.getModel()));
        StringBuilder sbUpdate = new StringBuilder();
        sbUpdate.append("UPDATE ").append(table.getInfo().getName()).append(" SET ");
        List<Column> tempList = new ArrayList<>();
        tempList.addAll(table.getColumnList());
        tempList.remove(table.getPrimary());
        for (int i = 0; i < tempList.size(); i++) {
            sbUpdate.append(tempList.get(i).getRealName()).append(" = ").append("#{").append(tempList.get(i).getName()).append(", jdbcType = ").append(GenUtils.jdbcType(tempList.get(i))).append("}");
            if (i < tempList.size() - 1) {
                sbUpdate.append(", ");
            }
        }
        sbUpdate.append(" WHERE ").append(table.getPrimary().getRealName()).append(" = #{").append(table.getPrimary().getName()).append(", jdbcType = ").append(GenUtils.jdbcType(table.getPrimary())).append("}");
        update.setContent(sbUpdate.toString());

        // delete节点
        Element delete = new Element("delete");
        delete.addAttribute(new Attribute("id", "delete"));
        delete.addAttribute(new Attribute("parameterType", genRecord.getModel()));
        delete.setContent("DELETE FROM " + table.getInfo().getName() + " WHERE " + table.getPrimary().getRealName() + " = #{" + table.getPrimary().getName() + ", jdbcType = " + GenUtils.jdbcType(table.getPrimary()) + "}");

        // deleteById节点
        Element deleteById = null;
        if (null != table.getPrimary()) {
            deleteById = new Element("delete");
            deleteById.addAttribute(new Attribute("id", "deleteById"));
            deleteById.addAttribute(new Attribute("parameterType", GenUtils.mapColumnType(table.getPrimary()).getName()));
            deleteById.setContent("DELETE FROM " + table.getInfo().getName() + " WHERE " + table.getPrimary().getRealName() + " = #{" + table.getPrimary().getName() + ", jdbcType = " + GenUtils.jdbcType(table.getPrimary()) + "}");
        }

        // 将所有节点添加到根节点中
        mapper.addElement(resultMap);
        mapper.addElement(insert);
        if (null != selectById) {
            mapper.addElement(selectById);
        }
        mapper.addElement(selectAll);
        mapper.addElement(update);
        mapper.addElement(delete);
        if (null != deleteById) {
            mapper.addElement(deleteById);
        }
        // 将跟节点添加到xml文件中
        xmlFile.addElement(mapper);

        // 保存当前生成记录
        genRecord.setMapperXml(getMapperXmlDir() + "/" + getMapperXmlFileName());
        GenLoader.getInstance().save(genRecord);

        return xmlFile;
    }
}
