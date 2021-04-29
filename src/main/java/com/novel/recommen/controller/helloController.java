package com.novel.recommen.controller;

import com.novel.recommen.model.NovelInfo;
import com.novel.recommen.model.Result;
import com.novel.recommen.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class helloController {
    @Autowired
    private NovelService novelService;
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    @RequestMapping("/hello")
    public String index() {
        NovelInfo novelInfo = new NovelInfo();
        novelInfo.setNovelName("zhimingpianc ");
        return novelService.insertNovel(novelInfo);
    }

    @RequestMapping("/getAllNovelList")
    public Result getAllNovelList() {
        NovelInfo novelInfo = new NovelInfo();
        Result result = new Result();
        List<NovelInfo> novelInfos = novelService.getAllNovelList(novelInfo);
        if (CollectionUtils.isEmpty(novelInfos)) {
            result.setStatus(FAIL);
            return result;
        }
        result.setStatus(SUCCESS);
        result.setObject(novelInfos);
        return result;

    }
}
