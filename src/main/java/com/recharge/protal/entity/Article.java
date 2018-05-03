package com.recharge.protal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer id;
    private String title;
    private String content;
    private String createDate;

}
