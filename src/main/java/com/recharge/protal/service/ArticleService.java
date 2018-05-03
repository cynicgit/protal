package com.recharge.protal.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.recharge.protal.entity.Article;
import com.recharge.protal.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public PageInfo<Article> getArticles(int page, int size) {
        PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.getArticles();

        return new PageInfo<>(articles);
    }

    public Article getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }
}
