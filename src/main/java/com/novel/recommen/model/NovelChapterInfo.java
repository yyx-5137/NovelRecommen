package com.novel.recommen.model;

import lombok.Data;

@Data
public class NovelChapterInfo {
    private int chapterId; //章节id
    private int authorId;  //作者id
    private String chapterNumId; //小说章节id 可用于判断章节的顺序 小说id_章节id_作者id
    private String chapterName;  //小说章节名称
    private String chapterUrl; //小说章节链接
}
