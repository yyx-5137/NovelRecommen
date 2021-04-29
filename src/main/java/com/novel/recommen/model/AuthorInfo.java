package com.novel.recommen.model;

import lombok.Data;

@Data
public class AuthorInfo {
    private int authorId; //作者id
    private String authorName; //作者笔名
    private String avatarUrl; //作者头像
    private String authorIntroduction; //作者简介

}
