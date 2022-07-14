package xyz.panyi.novel.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;
import xyz.panyi.novel.model.Chapter;

@Mapper
@Repository
public interface ChapterDao {
    @Insert("insert into chapter (novelId,pageIndex,originUrl,title,content,updateTime,extra) " +
            "values(#{novelId},#{pageIndex},#{originUrl}," +
            "#{title},#{content},#{updateTime},#{extra})")
    @SelectKey(statement = "select seq as id from sqlite_sequence where (name='chapter')",
            before = false, keyProperty = "id", resultType = int.class)
    int insertChapter(Chapter chapter);
}
