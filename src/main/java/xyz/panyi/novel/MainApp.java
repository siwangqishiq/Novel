package xyz.panyi.novel;

import xyz.panyi.novel.spider.Spider;
import xyz.panyi.novel.util.LogUtil;

public class MainApp {
    public static void main(String[] args){
        Spider spider = new Spider();
        spider.fetchNovel("http://www.xbqgxs.net/130_130827/");
    }
}//end class
