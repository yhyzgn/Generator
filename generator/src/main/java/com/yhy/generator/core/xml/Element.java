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
        String indent = "";
        return createElement(this, indent);
    }

    private String createElement(Element element, String indent) {
        // 生成头标签
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("<").append(element.getName());
        if (null != element.getAttributes() && !element.getAttributes().isEmpty()) {
            for (Attribute attr : element.getAttributes()) {
                sb.append(" ").append(attr.getName()).append("=\"").append(attr.getValue()).append("\"");
            }
        }

        if (null != element.getElements() && !element.getElements().isEmpty()) {
            // 如果有子节点，就递归生成子节点
            // 头标签结束符
            sb.append(">").append(System.getProperty("line.separator"));
            // 循环递归生成子节点
            for (int i = 0; i < element.getElements().size(); i++) {
                if (i < element.getElements().size() - 1) {
//                    sb.append(System.getProperty("line.separator"));
                }
                sb.append(createElement(element.getElements().get(i), indent + "\t"));
            }
            // 生成尾标签
            sb.append(indent).append("</").append(element.getName()).append(">");
            if (element.hashCode() != this.hashCode()) {
                sb.append(System.getProperty("line.separator"));
            }
        } else if (StringUtils.isNotEmpty(element.getContent())) {
            // 如果有文本内容，就添加文本内容
            // 头标签结束符
            sb.append(">").append(System.getProperty("line.separator"));
            // 生成文本
            sb.append(indent).append("\t").append(element.getContent()).append(System.getProperty("line.separator"));
            // 生成尾标签
            sb.append(indent).append("</").append(element.getName()).append(">");
            if (element.hashCode() != this.hashCode()) {
                sb.append(System.getProperty("line.separator"));
            }
        } else {
            // 如果没有子节点也没有文本内容，就把当前标签设置为单标签
            sb.append("/>").append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
