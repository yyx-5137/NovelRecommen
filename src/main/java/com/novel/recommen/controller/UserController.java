package com.novel.recommen.controller;

import com.novel.recommen.model.Result;
import com.novel.recommen.model.UserInfo;
import com.novel.recommen.service.UserService;
import com.novel.recommen.util.TypeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static com.novel.recommen.controller.NovelController.FAIL;
import static com.novel.recommen.controller.NovelController.SUCCESS;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    TypeUtils typeUtils = new TypeUtils();
    @RequestMapping(value ="/userRegister", method = RequestMethod.POST)
    public Result register(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param password
        System.out.println(map.get("userId")+"____"+map.get("password"));
        UserInfo userInfo = userService.register(map.get("userId"),map.get("password"));
        System.out.println(userInfo);
        Result result = new Result();
        if (ObjectUtils.isEmpty(userInfo)){
            result.setStatus(FAIL);
        }else {
            result.setStatus(SUCCESS);
        }
        result.setObject(userInfo);
        return result;
    }

    @RequestMapping(value ="/userLogin", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param password
        UserInfo userInfo = userService.login(map.get("userId"),map.get("password"));
        Result result = new Result();
        if (ObjectUtils.isEmpty(userInfo)){
            result.setStatus(FAIL);
        }else {
            result.setStatus(SUCCESS);
        }
        result.setObject(userInfo);
        return result;
    }
    @RequestMapping(value ="/updateUser", method = RequestMethod.POST)
    public Result updateUser(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param password
        UserInfo info = new UserInfo();
        info.setUserName(map.get("userName"));
        info.setUserId(map.get("userId"));
        info.setPassword(map.get("password"));
        info.setSex(typeUtils.StringToInt(map.get("sex")));
        info.setProfileUrl(map.get("profileUrl"));
        info.setUserIntroduction(map.get("userIntroduction"));
        UserInfo userInfo = userService.updateUserById(info);
        Result result = new Result();
        if (ObjectUtils.isEmpty(userInfo)){
            result.setStatus(FAIL);
        }else {
            result.setStatus(SUCCESS);
        }
        result.setObject(userInfo);
        return result;
    }
    @RequestMapping(value ="/updateImg", method = RequestMethod.POST)
    public Result UploadImg(@RequestBody Map<String, String> map) throws IOException {
        // param userId
        // param password
        String content = map.get("content");
        String path = map.get("path");
        content = content.substring(22);

        boolean isOk = userService.updateImg(content,path);
        Result result = new Result();
        if (isOk){
            result.setStatus(SUCCESS);
        }else{
            result.setStatus(FAIL);
        }

        return result;
    }
}
