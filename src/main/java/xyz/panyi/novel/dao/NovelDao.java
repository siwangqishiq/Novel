package xyz.panyi.novel.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;
import xyz.panyi.novel.model.Novel;

import java.util.List;

@Mapper
@Repository
public interface NovelDao {
    @Select("select nid from novel")
    public List<String> selectNids();

    @Insert("insert into novel (name,author,novelDesc,coverUrl,tag,chapterCount,originUrl,nid,updateTime,extra) " +
            "values(#{name},#{author},#{novelDesc}," +
            "#{coverUrl},#{tag},#{chapterCount},#{originUrl},#{nid},#{updateTime},#{extra})")
    @SelectKey(statement = "select seq as id from sqlite_sequence where (name='novel')",
            before = false, keyProperty = "id", resultType = int.class)
    int insertNovel(Novel novel);

    @Select("select * from novel order by updateTime desc")
    public List<Novel> queryNovels();
}


