package com.novel.recommen.service;

import com.novel.recommen.dao.NovelDao;
import com.novel.recommen.model.NovelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class NovelService {
    @Autowired
    private NovelDao novelDao;

    public String insertNovel(NovelInfo novelInfo) {
        int result = novelDao.novelInsert(novelInfo);
        if (result > 0) {
            return "success";

        } else {
            return "fail";
        }
    }
    public List<NovelInfo> getAllNovelList(NovelInfo novelInfo){
        List<NovelInfo> allNovelList = novelDao.getAll(novelInfo);
        if (CollectionUtils.isEmpty(allNovelList)){
            return Collections.EMPTY_LIST;
        }
        return allNovelList;
    }
}
