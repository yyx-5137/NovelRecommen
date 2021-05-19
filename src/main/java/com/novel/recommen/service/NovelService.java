package com.novel.recommen.service;

import com.google.gson.Gson;
import com.novel.recommen.dao.NovelDao;
import com.novel.recommen.model.*;
import com.novel.recommen.oss.OssClient;
import com.novel.recommen.util.TypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NovelService {
    TypeUtils typeUtils = new TypeUtils();
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

    public List<NovelInfo> getAllNovelList() {
        List<NovelInfo> allNovelList = novelDao.getAllNovelList();
        if (CollectionUtils.isEmpty(allNovelList)) {
            return Collections.EMPTY_LIST;
        }
        return allNovelList;
    }

    public List<NovelInfo> getTop10(String userId) throws IOException {
        List<NovelInfo> topList = new ArrayList<>();
        URL url = new URL("http://localhost:8081/getTop10");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = StringUtils.join("{\"userId\": ", typeUtils.StringToInt(userId), "}");
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Gson gson = new Gson();
            String jsonString = response.toString();
            MessageListInfo messageListInfo = gson.fromJson(jsonString, MessageListInfo.class);
            System.out.println(messageListInfo.getMsg());
            System.out.println(response.toString());
            topList = novelDao.getBookByRange(messageListInfo.getMsg());
        }
        return topList;

    }

    public String getBookContents() throws IOException {
        String contents = "";

        contents = OssClient.GetRandomBook();
        return contents;
    }

    public List<ChapterInfo> getBookPaperByCursor(int begin, int count, int bookId) {
        List<ChapterInfo> paperList = novelDao.getBookPaperByCount(bookId, begin, count);
        if (CollectionUtils.isEmpty(paperList)) {
            return Collections.EMPTY_LIST;
        }
        return paperList;
    }

    public List<ChapterInfo> getAllBookPaper(int bookId) {
        List<ChapterInfo> paperList = novelDao.getAllBookPaper(bookId);
        if (CollectionUtils.isEmpty(paperList)) {
            return Collections.EMPTY_LIST;
        }
        return paperList;
    }

    public List<ChapterInfo> getBookAllPaper(int bookId) {
        List<ChapterInfo> allPaperList = novelDao.getBookPaper(bookId);
        if (CollectionUtils.isEmpty(allPaperList)) {
            return Collections.EMPTY_LIST;
        }
        return allPaperList;
    }

    public boolean addBookShelfByUser(String userId, String bookId) {
        int count = novelDao.addBookShelfByUser(userId, typeUtils.StringToInt(bookId));
        if (count > 0) {
            return true;
        }
        return false;

    }

    public boolean removeBookShelfByUser(String userId, String bookId) {
        int count = novelDao.removeBookShelfByUser(userId, typeUtils.StringToInt(bookId));
        if (count > 0) {
            return true;
        }
        return false;

    }

    public List<NovelInfo> getBookShelfByUser(String userId) {
        List<NovelInfo> novelInfos = novelDao.getBookShelfByUser(userId);
        List<NovelInfo> unique = novelInfos.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(NovelInfo::getId))), ArrayList::new)
        );
        if (CollectionUtils.isEmpty(unique)) {
            return Collections.EMPTY_LIST;
        }
        return unique;
    }

    public boolean isAddBookShelf(String userId, String bookId) {
        List<BookShelfInfo> bookShelfInfos = novelDao.getBookShelfByBook(userId, typeUtils.StringToInt(bookId));
        if (CollectionUtils.isEmpty(bookShelfInfos)) {
            return false;
        }
        return true;
    }

    public List<NovelInfo> searchBook(String value) {
        List<NovelInfo> novelInfos = novelDao.searchBook(value);
        if (CollectionUtils.isEmpty(novelInfos)) {
            return Collections.EMPTY_LIST;
        }
        return novelInfos;
    }

    public List<NovelInfo> getTopBook() {
        List<NovelInfo> novelInfos = novelDao.getTopBook();
        if (CollectionUtils.isEmpty(novelInfos)) {
            return Collections.EMPTY_LIST;
        }
        return novelInfos;
    }

    public List<NovelInfo> getBookByTag(String tag) {
        List<NovelInfo> novelInfos = novelDao.getBookByTag(tag);
        if (CollectionUtils.isEmpty(novelInfos)) {
            return Collections.EMPTY_LIST;
        }
        return novelInfos;
    }

    public String doRate(String userId, String novelId, String rate) throws IOException {
        URL url = new URL("http://localhost:8081/doRate");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = StringUtils.join("{\"userId\": ", typeUtils.StringToInt(userId), ",\"novelId\": ", typeUtils.StringToInt(novelId), ", \"rating\":", typeUtils.StringToInt(rate), "}");
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Gson gson = new Gson();
            String jsonString = response.toString();
            MessageInfo messageInfo = gson.fromJson(jsonString, MessageInfo.class);
            System.out.println(messageInfo.getMsg());
            System.out.println(response.toString());
            return messageInfo.getMsg();
        }

    }

    public int getRate(String userId, String novelId) throws IOException {
        int rate = 0;
        URL url = new URL("http://localhost:8081/getRate");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = StringUtils.join("{\"userId\": ", typeUtils.StringToInt(userId), ",\"novelId\": ", typeUtils.StringToInt(novelId), "}");
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Gson gson = new Gson();
            String jsonString = response.toString();
            MessageInfo messageInfo = gson.fromJson(jsonString, MessageInfo.class);
            System.out.println(messageInfo.getMsg());
            System.out.println(response.toString());
            rate = typeUtils.StringToInt(messageInfo.getMsg());
        }
        return rate;
    }

    public boolean admin(String adminStatus, String bookId) {
        int count = novelDao.adminUpdate(typeUtils.StringToInt(adminStatus), typeUtils.StringToInt(bookId));
        if (count > 0) {
            return true;
        }
        return false;
    }


}
