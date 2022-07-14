package xyz.panyi.novel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovelController {

    @GetMapping(value = "hello")
    public String hello(){
        return "Hello Novel!";
    }
}
