package com.novel.recommen.dao;

import com.novel.recommen.model.BookShelfInfo;
import com.novel.recommen.model.ChapterInfo;
import com.novel.recommen.model.NovelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<NovelInfo> getBookByRange(List<Integer> bookRange) {
        String sql = "SELECT novel_info.id,novel_name,state,introduce,cover_url,tag,novel_info.create_time,rating,name FROM novel_info,author_info WHERE novel_info.author_id = author_info.id  AND admin_status = 1 and novel_info.id IN " + "(" + bookRange.toString().substring(1, bookRange.toString().length() - 1) + ")";

        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));
    }
    public List<NovelInfo> getAllNovelList(){
        String sql = "SELECT novel_info.id,novel_name,state,introduce,cover_url,tag,novel_info.create_time,rating,admin_status,author_info.`name` FROM novel_info,author_info WHERE novel_info.author_id = author_info.id ORDER BY novel_info.id";
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));
    }

    public List<ChapterInfo> getBookPaper(int bookId) {
        String sql = "SELECT chapter_name from novel_chapter_info where chapter_id like " + "\"" + bookId + "_%\"";
        System.out.println(sql);
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(ChapterInfo.class));
    }

    public List<ChapterInfo> getBookPaperByCount(int bookId, int begin, int count) {
        String sql = "select * from (SELECT * ,ROW_NUMBER() over(ORDER BY id) as rowNum from novel_chapter_info WHERE chapter_id like" + "\"" + bookId + "_%\") t1 where rowNum >=" + begin + " LIMIT " + count;
        System.out.println(sql);
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(ChapterInfo.class));
    }
    public List<ChapterInfo> getAllBookPaper(int bookId){
        String sql = "select * from (SELECT * ,ROW_NUMBER() over(ORDER BY id) as rowNum from novel_chapter_info WHERE chapter_id like \""+bookId+"_%\") t1 LIMIT 100";
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(ChapterInfo.class));

    }

    public int addBookShelfByUser(String userId, int bookId) {
        String sql = "INSERT INTO book_shelf(user_id,novel_id) VALUES (:userId,:bookId)";
        ps.addValue("userId", userId);
        ps.addValue("bookId", bookId);
        return jdbcTemplate.update(sql, ps);
    }
    public int removeBookShelfByUser(String userId, int bookId) {
        String sql = "DELETE FROM book_shelf WHERE user_id = :userId AND novel_id = :bookId";
        ps.addValue("userId", userId);
        ps.addValue("bookId", bookId);
        return jdbcTemplate.update(sql, ps);
    }

    public List<NovelInfo> getBookShelfByUser(String userId) {
        String sql = "SELECT novel_info.id,novel_name,state,introduce,cover_url,tag,novel_info.create_time,rating FROM novel_info,book_shelf WHERE novel_info.id = book_shelf.novel_id and user_id = :userId AND admin_status = 1";
        ps.addValue("userId", userId);
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));
    }

    public List<BookShelfInfo> getBookShelfByBook(String userId, int bookId) {
        String sql = "SELECT * FROM book_shelf WHERE user_id = :userId AND novel_id = :novelId";
        ps.addValue("userId", userId);
        ps.addValue("novelId", bookId);
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(BookShelfInfo.class));

    }

    public List<NovelInfo> searchBook(String value) {
        String sql = "SELECT novel_info.id,novel_name,state,introduce,cover_url,tag,novel_info.create_time,rating,author_info.`name` FROM novel_info,author_info WHERE novel_info.author_id = author_info.id AND admin_status = 1 and (novel_info.novel_name LIKE \"%" + value + "%\" OR author_info.`name` LIKE \"%" + value + "%\")";
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));
    }

    public List<NovelInfo> getTopBook() {
        String sql = "SELECT novel_info.id,novel_name,state,introduce,cover_url,tag,novel_info.create_time,rating,author_info.`name` FROM novel_info,author_info WHERE novel_info.author_id = author_info.id ORDER BY novel_info.id DESC";
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));

    }
    public List<NovelInfo> getBookByTag(String tag){
        String sql = "SELECT novel_info.id,novel_name,state,introduce,cover_url,tag,novel_info.create_time,rating,author_info.`name` FROM novel_info,author_info WHERE novel_info.author_id = author_info.id and tag = :tag AND admin_status = 1";
        ps.addValue("tag",tag);
        return jdbcTemplate.query(sql, ps, BeanPropertyRowMapper.newInstance(NovelInfo.class));

    }

    public int adminUpdate(int adminStatus,int bookId){
        String sql = "UPDATE novel_info SET admin_status = :adminStatus WHERE id = :bookId";
        ps.addValue("adminStatus",adminStatus);
        ps.addValue("bookId",bookId);
        return jdbcTemplate.update(sql, ps);

    }

    private String getTableName(int novelId) {
        String tableName = "novel_info";
        return tableName;
    }

}
