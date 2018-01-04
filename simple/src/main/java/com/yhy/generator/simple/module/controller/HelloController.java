package com.yhy.generator.simple.module.controller;

import com.yhy.generator.api.db.TableApi;
import com.yhy.generator.api.loader.TableLoader;
import com.yhy.generator.generator.MapperXmlGenerator;
import com.yhy.generator.helper.GeneratorHelper;
import com.yhy.generator.model.table.Column;
import com.yhy.generator.model.table.Table;
import com.yhy.generator.model.table.TableInfo;
import com.yhy.generator.simple.gen.model.article.Article;
import com.yhy.generator.simple.gen.service.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-26 15:44
 * version: 1.0.0
 * desc   :
 */
@Controller
@RequestMapping("/")
public class HelloController {

    @Resource
    private ArticleService articleService;

    @RequestMapping("infos")
    @ResponseBody
    public List<TableInfo> infos() {
        List<TableInfo> result = new TableApi().loadTableInfoList();
        return result;
    }

    @RequestMapping("columns/{name}")
    @ResponseBody
    public List<Column> columns(@PathVariable("name") String name) {
        List<Column> result = new TableApi().getColumnList(name);
        return result;
    }

    @RequestMapping("tables")
    @ResponseBody
    public List<Table> tables() {
        return new TableLoader().allTableList();
    }

    @RequestMapping("xml")
    @ResponseBody
    public String xml() {
        MapperXmlGenerator generator = new MapperXmlGenerator(tables().get(0));
        generator.generate();
        return "哈哈";
    }

    @RequestMapping("gen")
    @ResponseBody
    public String gen() {
        GeneratorHelper helper = new GeneratorHelper();
        helper.generate(tables().get(0));
        return "哈哈";
    }

    @RequestMapping("insert")
    @ResponseBody
    public Article insert() throws Exception {
        Article article = new Article();
        article.setId(2);
        article.setTitle("文章标题");
        article.setContent("文章内容");
        article.setCreateTime(new Date(System.currentTimeMillis()));
        article.setPrice(new BigDecimal(12.50));
        article.setSort(1);
        article.setUpdateTime(new Date(System.currentTimeMillis()));
        article.setUserId(2222L);

        articleService.insert(article);

        return article;
    }
}
