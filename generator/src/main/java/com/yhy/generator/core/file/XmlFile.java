package com.yhy.generator.core.file;

import com.yhy.generator.core.file.abs.AbsFile;
import com.yhy.generator.core.xml.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-27 14:54
 * version: 1.0.0
 * desc   : Xml文件
 */
public class XmlFile implements AbsFile {
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String DOC_TYPE = "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
    // 所有节点
    private List<Element> elementList;

    public XmlFile() {
        this(new ArrayList<>());
    }

    public XmlFile(List<Element> elementList) {
        this.elementList = elementList;
    }

    public XmlFile setElementList(List<Element> elementList) {
        this.elementList = elementList;
        return this;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public XmlFile addElement(Element element) {
        elementList.add(element);
        return this;
    }

    public XmlFile addElementList(List<Element> elementList) {
        this.elementList.addAll(elementList);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(XML_HEADER).append(System.getProperty("line.separator")).append(DOC_TYPE).append(System.getProperty("line.separator"));
        if (null != elementList && !elementList.isEmpty()) {
            for (Element element : elementList) {
                sb.append(element.toString());
            }
        }
        return sb.toString();
    }
}
