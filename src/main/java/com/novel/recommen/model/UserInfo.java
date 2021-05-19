package com.novel.recommen.model;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String userId;
    private String userName;
    private String password;
    private int sex;
    private int role;
    private String likeType;
    private String profileUrl;
    private String userIntroduction;
}
