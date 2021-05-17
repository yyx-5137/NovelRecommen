package com.novel.recommen.controller;

import com.novel.recommen.model.NovelInfo;
import com.novel.recommen.model.Result;
import com.novel.recommen.service.NovelService;
import com.rongzhiweilai.extension.annotation.JSONParam;
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

    @RequestMapping("/hello")
    public String index() {
        NovelInfo novelInfo = new NovelInfo();
        novelInfo.setNovelName("zhimingpianc ");
        return novelService.insertNovel(novelInfo);
    }

    @RequestMapping(value = "/getAllNovelList", method = RequestMethod.POST)
    public Result getAllNovelList(@JSONParam String userId) {
        System.out.println("userid:" + userId);
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


    @RequestMapping(value = "/doRate", method = RequestMethod.POST)
    public Result doRate(@JSONParam String userId) throws IOException {
        URL url = new URL("http://localhost:8081/doRate");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"userId\": 123,\"novelId\": 123312, \"rating\":4}";
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
            System.out.println(response.toString());
        }
        Result result = new Result();
        result.setStatus(SUCCESS);
        return result;

    }

    @RequestMapping("/getBookContents")
    public Result getContents() throws IOException {
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookContents());
        return result;
    }
    @RequestMapping(value ="/getAllBookPaper", method = RequestMethod.POST)
    public Result getAllBookPaper(@RequestBody Map<String, Integer> map) throws IOException {
        // param begin 1 , @JSONParam int begin, @JSONParam int count
        // param count 10
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookAllPaper(map.get("bookId")));
        return result;
    }

    @RequestMapping(value ="/getBookPaperByCursor", method = RequestMethod.POST)
    public Result getBookPaperByCursor(@RequestBody Map<String, Integer> map) throws IOException {
        // param begin 1 , @JSONParam int begin, @JSONParam int count
        // param count 10
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookPaperByCursor(map.get("begin"),map.get("count"),map.get("bookId")));
        return result;
    }
    @RequestMapping(value ="/addBookShelfByUser", method = RequestMethod.POST)
    public Result addBookShelfByUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.addBookShelfByUser(map.get("userId"),map.get("bookId")));
        return result;
    }
    @RequestMapping(value ="/removeBookShelfByUser", method = RequestMethod.POST)
    public Result removeBookShelfByUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param bookId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.removeBookShelfByUser(map.get("userId"),map.get("bookId")));
        return result;
    }
    @RequestMapping(value ="/getBookShelfByUser", method = RequestMethod.POST)
    public Result getBookShelfByUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookShelfByUser(map.get("userId")));
        return result;
    }

    @RequestMapping(value ="/isAddBookShelf", method = RequestMethod.POST)
    public Result isAddBookShelf(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.isAddBookShelf(map.get("userId"),map.get("bookId")));
        return result;
    }

    @RequestMapping(value ="/searchBook", method = RequestMethod.POST)
    public Result searchBook(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.searchBook(map.get("value")));
        return result;
    }
    @RequestMapping(value ="/getTopBook", method = RequestMethod.POST)
    public Result getTopBook(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getTopBook());
        return result;
    }
    @RequestMapping(value ="/getBookByTag", method = RequestMethod.POST)
    public Result getBookByTag(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        Result result = new Result();
        result.setStatus(SUCCESS);
        result.setObject(novelService.getBookByTag(map.get("tag")));
        return result;
    }



}
