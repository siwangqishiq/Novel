package xyz.panyi.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.panyi.novel.dao.NovelDao;
import xyz.panyi.novel.model.Novel;
import xyz.panyi.novel.model.Resp;

import java.util.List;

@Service
public class NovelService {

    @Autowired
    private NovelDao novelDao;

    public Resp<List<Novel>> queryAllNovels(){
        return Resp.genResp(novelDao.queryNovels());
    }
}
