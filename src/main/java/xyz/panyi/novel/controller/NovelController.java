package xyz.panyi.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.panyi.novel.model.Novel;
import xyz.panyi.novel.model.Resp;
import xyz.panyi.novel.service.NovelService;

import java.util.List;

@RestController
public class NovelController {

    @Autowired
    private NovelService novelService;

    @GetMapping(value = "hello")
    public String hello(){
        return "Hello Novel!";
    }

    @GetMapping(value = "novels")
    public Resp<List<Novel>> novels(){
        return novelService.queryAllNovels();
    }
}
