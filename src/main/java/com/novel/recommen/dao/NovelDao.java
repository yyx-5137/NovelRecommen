package com.novel.recommen.dao;

import com.novel.recommen.model.NovelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NovelDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private MapSqlParameterSource ps = new MapSqlParameterSource();


    public int novelInsert(NovelInfo novelInfo) {
        String sql = "INSERT INTO novel_info (novel_name) VALUES (:novelName)";
        ps.addValue("novelName", novelInfo.getNovelName());
        return jdbcTemplate.update(sql, ps);

    }

    public List<NovelInfo> getAll(NovelInfo novelInfo) {
        String sql = "SELECT * FROM novel_info";
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));
    }

    private String getTableName(int novelId) {
        String tableName = "novel_info";
        return tableName;
    }

}
