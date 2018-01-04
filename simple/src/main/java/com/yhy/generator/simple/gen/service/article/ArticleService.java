package com.yhy.generator.simple.gen.service.article;

import com.yhy.generator.simple.gen.model.article.Article;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-04 10:27:25
 * version: 1.0.0
 * desc   : 文章表
 */
public interface ArticleService {
	Integer insert(Article article) throws Exception;

	Article selectById(Integer id) throws Exception;

	List<Article> selectAll() throws Exception;

	Integer update(Article article) throws Exception;

	Integer delete(Article article) throws Exception;

	Integer deleteById(Integer id) throws Exception;
}