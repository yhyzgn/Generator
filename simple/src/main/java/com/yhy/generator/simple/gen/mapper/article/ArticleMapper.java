package com.yhy.generator.simple.gen.mapper.article;

import com.yhy.generator.simple.gen.model.article.Article;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2018-01-04 15:26:16
 * version: 1.0.0
 * desc   : 文章表
 */
@Repository
public interface ArticleMapper {
	Integer insert(Article article) throws Exception;

	Article selectById(Integer id) throws Exception;

	List<Article> selectAll() throws Exception;

	Integer update(Article article) throws Exception;

	Integer delete(Article article) throws Exception;

	Integer deleteById(Integer id) throws Exception;
}