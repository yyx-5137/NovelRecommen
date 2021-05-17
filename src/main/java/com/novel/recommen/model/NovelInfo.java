package com.novel.recommen.model;

import lombok.Data;

@Data
public class NovelInfo {
    private int id; //小说id
    private String novelName; //小说名字
    private String state;  //小说状态：连载/完结
    private String introduce;  //小说简介
    private String coverUrl;  //小说封面
    private String tag;  //小说标签
    private int rating; //评分
    private String name;//作者名
}
