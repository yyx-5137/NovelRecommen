package com.novel.recommen.model;

import lombok.Data;

@Data
public class NovelInfo {
    private int novelId; //小说id
    private String novelName; //小说名字
    private int authorId;  //作者名字
    private String state;  //小说状态：连载/完结
    private String introduce;  //小说简介
    private String coverUrl;  //小说封面
    private String tag;  //小说标签
}
