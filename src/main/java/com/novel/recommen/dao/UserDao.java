package com.novel.recommen.dao;

import com.novel.recommen.model.NovelInfo;
import com.novel.recommen.model.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private MapSqlParameterSource ps = new MapSqlParameterSource();

    public UserInfo addUserByInfo(UserInfo userInfo) {
        String sql = "INSERT INTO user_info(user_id,`password`) VALUES(:userId,:password)";
        ps.addValue("userId", userInfo.getUserId());
        ps.addValue("password", userInfo.getPassword());
        int count = jdbcTemplate.update(sql, ps);
        if (count > 0) {
            return userInfo;
        }
        return null;
    }

    public UserInfo getUserById(String userId) {
        String sql = "SELECT * from user_info WHERE user_id = :userId";
        ps.addValue("userId", userId);
        List<UserInfo> userInfos = jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(UserInfo.class));
        if (CollectionUtils.isEmpty(userInfos)){
            return null;
        }
        return userInfos.get(0);
    }

    public UserInfo updateUser(UserInfo userInfo) {
        String sql = "UPDATE user_info SET ";
         List<String> l = new ArrayList<>();
        if (StringUtils.isNotBlank(userInfo.getPassword())) {
            l.add(StringUtils.join( "`password` = ", "\"", userInfo.getPassword(), "\""));
        }
        if (StringUtils.isNotBlank(userInfo.getUserName())) {
            l.add(StringUtils.join(  "user_name = ", "\"", userInfo.getUserName(), "\""));
        }
        if (userInfo.getSex() != 0) {
            l.add(StringUtils.join(  "sex = ", userInfo.getSex()));
        }
        if (StringUtils.isNotBlank(userInfo.getProfileUrl())) {
            l.add(StringUtils.join(  "profile_url = ", "\"", userInfo.getProfileUrl(), "\""));
        }
        if (StringUtils.isNotBlank(userInfo.getUserIntroduction())) {
            l.add(StringUtils.join( "user_introduction = ", "\"", userInfo.getUserIntroduction(), "\""));
        }
        sql = StringUtils.join(sql,l.toString().substring(1,l.toString().length()-1));

        sql = StringUtils.join(sql, " WHERE user_id = ", "\"", userInfo.getUserId(), "\"");
        int count = jdbcTemplate.update(sql, ps);
        if (count > 0){
            return userInfo;
        }
        return null;

    }


}
