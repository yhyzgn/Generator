package com.yhy.generator.simple.gen.service.article.impl;

import com.yhy.generator.simple.gen.mapper.article.ArticleMapper;
import com.yhy.generator.simple.gen.model.article.Article;
import com.yhy.generator.simple.gen.service.article.ArticleService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-04 15:26:16
 * version: 1.0.0
 * desc   : 文章表
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper articleMapper;

	public Integer insert(Article article) throws Exception {
		return articleMapper.insert(article);
	}

	public Article selectById(Integer id) throws Exception {
		return articleMapper.selectById(id);
	}

	public List<Article> selectAll() throws Exception {
		return articleMapper.selectAll();
	}

	public Integer update(Article article) throws Exception {
		return articleMapper.update(article);
	}

	public Integer delete(Article article) throws Exception {
		return articleMapper.delete(article);
	}

	public Integer deleteById(Integer id) throws Exception {
		return articleMapper.deleteById(id);
	}
}