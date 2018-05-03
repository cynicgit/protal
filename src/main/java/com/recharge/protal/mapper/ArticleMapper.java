package com.recharge.protal.mapper;

import com.recharge.protal.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    List<Article> getArticles();

    Article getArticleById(@Param("id") Integer id);
}
