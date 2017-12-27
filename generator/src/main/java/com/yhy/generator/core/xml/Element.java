package com.yhy.generator.core.xml;

import com.yhy.generator.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 16:16
 * version: 1.0.0
 * desc   :
 */
public class Element {
    private String name;
    private List<Attribute> attributes;
    private List<Element> elements;
    private String content;

    public Element(String name) {
        this.name = name;
        attributes = new ArrayList<>();
        elements = new ArrayList<>();
    }

    public Element addAttribute(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }

    public Element addElement(Element element) {
        elements.add(element);
        return this;
    }

    public String getName() {
        return name;
    }

    public Element setName(String name) {
        this.name = name;
        return this;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Element setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public List<Element> getElements() {
        return elements;
    }

    public Element setElements(List<Element> elements) {
        this.elements = elements;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Element setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return createElement(this);
    }

    private String createElement(Element element) {
        StringBuffer sb = new StringBuffer();

        sb.append("<").append(element.getName());
        if (null != element.getAttributes() && !element.getAttributes().isEmpty()) {
            for (Attribute attr : element.getAttributes()) {
                sb.append(" ").append(attr.getName()).append("=\"").append(attr.getValue()).append("\"");
            }
        }
        sb.append(">").append(System.getProperty("line.separator"));

        if (StringUtils.isNotEmpty(element.getContent())) {
            sb.append("\t").append(element.getContent()).append(System.getProperty("line.separator"));
        } else if (null != element.getElements() && !element.getElements().isEmpty()) {

        }
        sb.append("</").append(element.getName()).append(">");

        return sb.toString();
    }
}
