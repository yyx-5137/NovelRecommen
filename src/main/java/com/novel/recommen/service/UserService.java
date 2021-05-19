package com.novel.recommen.service;

import com.novel.recommen.dao.UserDao;
import com.novel.recommen.model.UserInfo;
import com.novel.recommen.oss.OssClient;
import com.novel.recommen.util.TypeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    OssClient ossClient = new OssClient();

    public UserInfo register(String userId, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setPassword(password);
        UserInfo registeredUser = userDao.getUserById(userId);
        if (ObjectUtils.isNotEmpty(registeredUser)) {
            return null;
        }
        System.out.println(registeredUser);
        System.out.println(userDao.addUserByInfo(userInfo));
        return userDao.addUserByInfo(userInfo);

    }

    public UserInfo login(String userId, String password) {
        UserInfo registeredUser = userDao.getUserById(userId);
        System.out.println("registeredUser:"+registeredUser);
        System.out.println("registeredUser isempty:"+ObjectUtils.isEmpty(registeredUser));
        if (ObjectUtils.isEmpty(registeredUser)) {
            return null;
        }
        if (!registeredUser.getPassword().equals(password)) {
            return null;
        }
        return registeredUser;
    }

    public UserInfo updateUserById(UserInfo userInfo) {
        return userDao.updateUser(userInfo);
    }

    public boolean updateImg(String content,String path){
        byte[] data = TypeUtils.Base64ToImage(content);
        OssClient.UpdateImg(data,path);
        return true;
    }

}
