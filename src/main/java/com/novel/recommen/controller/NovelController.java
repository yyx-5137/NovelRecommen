package com.novel.recommen.controller;

import com.novel.recommen.model.NovelInfo;
import com.novel.recommen.model.Result;
import com.novel.recommen.service.NovelService;
import com.novel.recommen.service.UserService;
import com.rongzhiweilai.extension.annotation.JSONParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
public class NovelController {
    @Autowired
    private NovelService novelService;
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String SUCCESSMESSAGE = "OK";


    @RequestMapping("/hello")
    public String index() {
        NovelInfo novelInfo = new NovelInfo();
        novelInfo.setNovelName("zhimingpianc ");
        return novelService.insertNovel(novelInfo);
    }

    @RequestMapping(value = "/getAllNovelList", method = RequestMethod.POST)
    public Result getAllNovelList(@JSONParam String id) {
        System.out.println("id:" + id);
        Result result = new Result();
        List<NovelInfo> novelInfos = novelService.getAllNovelList();
        if (CollectionUtils.isEmpty(novelInfos)) {
            result.setStatus(FAIL);
            return result;
        }
        result.setStatus(SUCCESS);
        result.setObject(novelInfos);
        return result;

    }

    @RequestMapping(value = "/getUserTop", method = RequestMethod.POST)
    public Result getUserTop(@JSONParam String id) throws IOException {
        System.out.println("userid:" + id);
        Result result = new Result();
        List<NovelInfo> novelInfos = novelService.getTop10(id);
        if (CollectionUtils.isEmpty(novelInfos)) {
            novelInfos = novelService.getAllNovelList();
        }
        result.setStatus(SUCCESS);
        result.setObject(novelInfos);
        return result;

    }


    @RequestMapping(value = "/doRate", method = RequestMethod.POST)
    public Result doRate(@RequestBody Map<String, String> map) throws IOException {
        System.out.println(map.get("id"));
        System.out.println(map.get("rate"));
        System.out.println(map.get("bookId"));
        String r = novelService.doRate(map.get("id"), map.get("bookId"), map.get("rate"));
        Result result = new Result();
        if (r.equals(SUCCESSMESSAGE)) {
            result.setStatus(SUCCESS);
            result.setObject(r);
        } else {
            result.setStatus(FAIL);
        }
        return result;

    }

    @RequestMapping(value = "/getRate", method = RequestMethod.POST)
    public Result getRate(@RequestBody Map<String, String> map) throws IOException {
        System.out.println("id:" + map.get("id"));
        System.out.println("bookId" + map.get("bookId"));
        int r = novelService.getRate(map.get("id"), map.get("bookId"));
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(r);
        return result;

    }

    @RequestMapping("/getBookContents")
    public Result getContents() throws IOException {
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookContents());
        return result;
    }

    @RequestMapping(value = "/getAllBookPaper", method = RequestMethod.POST)
    public Result getAllBookPaper(@RequestBody Map<String, Integer> map) throws IOException {
        // param begin 1 , @JSONParam int begin, @JSONParam int count
        // param count 10
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookAllPaper(map.get("bookId")));
        return result;
    }

    @RequestMapping(value = "/getBookPaperByCursor", method = RequestMethod.POST)
    public Result getBookPaperByCursor(@RequestBody Map<String, Integer> map) throws IOException {
        // param begin 1 , @JSONParam int begin, @JSONParam int count
        // param count 10
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookPaperByCursor(map.get("begin"), map.get("count"), map.get("bookId")));
        return result;
    }

    @RequestMapping(value = "/addBookShelfByUser", method = RequestMethod.POST)
    public Result addBookShelfByUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.addBookShelfByUser(map.get("userId"), map.get("bookId")));
        return result;
    }

    @RequestMapping(value = "/removeBookShelfByUser", method = RequestMethod.POST)
    public Result removeBookShelfByUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.removeBookShelfByUser(map.get("userId"), map.get("bookId")));
        return result;
    }

    @RequestMapping(value = "/getBookShelfByUser", method = RequestMethod.POST)
    public Result getBookShelfByUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookShelfByUser(map.get("userId")));
        return result;
    }

    @RequestMapping(value = "/isAddBookShelf", method = RequestMethod.POST)
    public Result isAddBookShelf(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.isAddBookShelf(map.get("userId"), map.get("bookId")));
        return result;
    }

    @RequestMapping(value = "/searchBook", method = RequestMethod.POST)
    public Result searchBook(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.searchBook(map.get("value")));
        return result;
    }

    @RequestMapping(value = "/getTopBook", method = RequestMethod.POST)
    public Result getTopBook(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getTopBook());
        return result;
    }

    @RequestMapping(value = "/getBookByTag", method = RequestMethod.POST)
    public Result getBookByTag(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookByTag(map.get("tag")));
        return result;
    }
    @RequestMapping(value = "/adminBook", method = RequestMethod.POST)
    public Result adminBook(@RequestBody Map<String, String> map) throws IOException {
        System.out.println(map.get("adminStatus")+"______"+map.get("bookId"));
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.admin(map.get("adminStatus"),map.get("bookId")));
        return result;
    }


}
