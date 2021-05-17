package com.novel.recommen.service;

import com.novel.recommen.dao.NovelDao;
import com.novel.recommen.model.BookShelfInfo;
import com.novel.recommen.model.ChapterInfo;
import com.novel.recommen.model.NovelInfo;
import com.novel.recommen.oss.OssClient;
import com.novel.recommen.util.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NovelService {
    TypeUtils typeUtils = new TypeUtils();
    @Autowired
    private NovelDao novelDao;
    OssClient ossClient = new OssClient();
    public String insertNovel(NovelInfo novelInfo) {
        int result = novelDao.novelInsert(novelInfo);
        if (result > 0) {
            return "success";

        } else {
            return "fail";
        }
    }
    public List<NovelInfo> getAllNovelList(NovelInfo novelInfo){
        List<Integer> bookRange = new ArrayList<>();
        bookRange.add(1);
        bookRange.add(2);
        bookRange.add(3);
        bookRange.add(4);
        bookRange.add(5);
        bookRange.add(6);
        bookRange.add(7);
        bookRange.add(8);
        bookRange.add(9);
        List<NovelInfo> allNovelList = novelDao.getBookByRange(novelInfo,bookRange);
        if (CollectionUtils.isEmpty(allNovelList)){
            return Collections.EMPTY_LIST;
        }
        return allNovelList;
    }
    public String getBookContents() throws IOException {
        String contents = "";

        contents = ossClient.GetRandomBook();
        return contents;
    }

    public List<ChapterInfo> getBookPaperByCursor(int begin,int count,int bookId){
        List<ChapterInfo> paperList = novelDao.getBookPaperByCount(bookId,begin,count);
        if (CollectionUtils.isEmpty(paperList)){
            return Collections.EMPTY_LIST;
        }
        return paperList;
    }
    public List<ChapterInfo> getAllBookPaper(int bookId){
        List<ChapterInfo> paperList = novelDao.getAllBookPaper(bookId);
        if (CollectionUtils.isEmpty(paperList)){
            return Collections.EMPTY_LIST;
        }
        return paperList;
    }

    public List<ChapterInfo> getBookAllPaper(int bookId){
        List<ChapterInfo> allPaperList = novelDao.getBookPaper(bookId);
        if (CollectionUtils.isEmpty(allPaperList)){
            return Collections.EMPTY_LIST;
        }
        return allPaperList;
    }

    public boolean addBookShelfByUser(String userId,String bookId){
        int count = novelDao.addBookShelfByUser(userId,typeUtils.StringToInt(bookId));
        if (count > 0){
            return true;
        }
        return false;

    }
    public boolean removeBookShelfByUser(String userId,String bookId){
        int count = novelDao.removeBookShelfByUser(userId,typeUtils.StringToInt(bookId));
        if (count > 0){
            return true;
        }
        return false;

    }

    public List<NovelInfo> getBookShelfByUser(String userId){
        List<NovelInfo> novelInfos = novelDao.getBookShelfByUser(userId);
        List<NovelInfo> unique = novelInfos.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(NovelInfo::getId))), ArrayList::new)
        );
        if (CollectionUtils.isEmpty(unique)){
            return Collections.EMPTY_LIST;
        }
        return unique;
    }

    public boolean isAddBookShelf(String userId,String bookId){
        List<BookShelfInfo> bookShelfInfos = novelDao.getBookShelfByBook(userId,typeUtils.StringToInt(bookId));
        if (CollectionUtils.isEmpty(bookShelfInfos)){
            return false;
        }
        return true;
    }

    public List<NovelInfo> searchBook(String value){
        List<NovelInfo> novelInfos = novelDao.searchBook(value);
        if (CollectionUtils.isEmpty(novelInfos)){
            return Collections.EMPTY_LIST;
        }
        return novelInfos;
    }
    public List<NovelInfo> getTopBook(){
        List<NovelInfo> novelInfos = novelDao.getTopBook();
        if (CollectionUtils.isEmpty(novelInfos)){
            return Collections.EMPTY_LIST;
        }
        return novelInfos;
    }
    public List<NovelInfo> getBookByTag(String tag){
        List<NovelInfo> novelInfos = novelDao.getBookByTag(tag);
        if (CollectionUtils.isEmpty(novelInfos)){
            return Collections.EMPTY_LIST;
        }
        novelInfos.forEach(o -> {
            System.out.println("小说列表："+o.getNovelName());
        });
        return novelInfos;
    }
}
