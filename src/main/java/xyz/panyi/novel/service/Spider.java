package xyz.panyi.novel.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.panyi.novel.dao.ChapterDao;
import xyz.panyi.novel.dao.NovelDao;
import xyz.panyi.novel.model.Chapter;
import xyz.panyi.novel.model.Novel;
import xyz.panyi.novel.util.LogUtil;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 爬虫
 */
@Scope(value = "prototype")
@Component
public class Spider {
    private static final String BASE_URL = "http://www.xbqgxs.net/rank/";
    private static final String BASE_URL2 = "http://www.xbqgxs.net/rank/monthvisit/";
    private static final String BASE_URL3 = "http://www.xbqgxs.net/rank/weekvisit/";
    private static final String BASE_URL4 = "http://www.xbqgxs.net/rank/dayvisit/"; //每日

    private static final Logger logger = LogUtil.buildLogger("Spider");

    private static final String AUTHOR_HEADER = "作 者：";

    private static final String P_HEADER = "  ";

    //已经入库的小说 用于重 防止重复拉取
    private Set<String> nidSets = new HashSet<String>();

    @Autowired
    private NovelDao novelDao;

    @Autowired
    private ChapterDao chapterDao;

    /**
     * 从
     * @param
     */
    public void parseComposePage(){
        doParseCompose(BASE_URL);
        doParseCompose(BASE_URL2);
        doParseCompose(BASE_URL3);
        doParseCompose(BASE_URL4);
    }

    private void doParseCompose(final String baseUrl){
        logger.info("parseComposePage : " + baseUrl);

        prepareNidSets();

        try {
            Document doc = Jsoup.connect(baseUrl).get();
            Elements mainElements = doc.getElementsByClass("recentread-main");
            int totalSize = mainElements.size();

            for(int i = 0 ; i < totalSize;i++){
                final Element element = mainElements.get(i);
                Elements tagLinks = element.getElementsByTag("a");
                Element tagLink = tagLinks.first();
                if(tagLink == null){
                    continue;
                }

                String name = tagLink.text();
                String link = tagLink.absUrl("href");
                logger.info("name : " + name);
                logger.info("link : " + link);

                final String nid = findNidFromUrl(link);
                if(nidSets.contains(nid)){
                    logger.info(name + " " + link + " has been pulled skip this nid = " + nid);
                    continue;
                }

                fetchNovel(link , i , totalSize);
                nidSets.add(nid);
            }//end for i
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     *  抓取小说内容
     * @param novelUrl
     */
    public void fetchNovel(final String novelUrl ,int novelIndex , int totalNovel){
        LogUtil.log("pull : " + novelUrl);

        try {
            Document doc = Jsoup.connect(novelUrl).get();
            Elements mainElems = doc.getElementsByClass("info-main");
            Element mainElem = mainElems.first();
            final String title = mainElem.getElementsByTag("h1").first().text();
            LogUtil.log("title : " + title);

            final String img = mainElem.getElementsByTag("img").first().absUrl("data-original");
            LogUtil.log("img : " + img);

            String authorStr = mainElem.getElementsByClass("w100 dispc").first()
                    .getElementsByTag("span").first().text();
            String author = authorNameFilter(authorStr);
            LogUtil.log("author : " + author);

            final Element tagEs = doc.getElementsByClass("info-title")
                    .first();
            Elements as = tagEs.getElementsByTag("a");
            String tag = null;
            for(Element e : as){
                String hrefVal = e.attr("href");
                if(hrefVal != null && hrefVal.contains("sort")){
                    tag = e.text();
                    break;
                }
            }
            LogUtil.log("tag : " + tag);

            Element pElement = mainElem.getElementsByClass("info-main-intro")
                    .first().getElementsByTag("p").first();
            String desc = pElement.text();
            LogUtil.log("desc : " + desc);

            Element chapterElements = doc.getElementsByClass("info-chapters flex flex-wrap")
                    .last();
            Elements chs = chapterElements.getElementsByTag("a");

            final Novel novel = new Novel();
            novel.setAuthor(author);
            novel.setName(title);
            novel.setNovelDesc(desc);
            novel.setCoverUrl(img);
            novel.setOriginUrl(novelUrl);
            novel.setTag(tag);
            novel.setNid(findNidFromUrl(novelUrl));
            novel.setChapterCount(chs.size());
            novel.setUpdateTime(System.currentTimeMillis());

            LogUtil.log("novel : " + novel);
            int result = novelDao.insertNovel(novel);
            logger.info("insert db result " + result +" id = " + novel.getId());

            //章节数据
            LogUtil.log("chapter count : " + chs.size());
            for(int i = 0 ; i < chs.size();i++){
                Element element = chs.get(i);

                final String chapterUrl = element.absUrl("href");
                final String chapterTitle = element.text();
                LogUtil.log("chapter title: " + chapterTitle);
                LogUtil.log("href:" + chapterUrl);

                LogUtil.log("chapter catch "+(i + 1)  +" / " + chs.size());
                fetchChapter(chapterUrl , novel ,i ,chapterTitle , String.format("%d / %d" ,
                        novelIndex + 1 , totalNovel));

                sleepMoment();
            }//end for i
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 抓取章节内容
     * @param chapterUrl
     */
    public void fetchChapter(final String chapterUrl , final Novel novel , int pageIndex ,
                             final String chapterTitle , final String totalProgress){
        try {
            Document doc = Jsoup.connect(chapterUrl).get();
            Elements ps = doc.getElementById("article").getElementsByTag("p");

            StringBuffer contentBuilder = new StringBuffer();
            for(Element e : ps){
                contentBuilder.append(P_HEADER).append(e.text()).append("\n");
            }//end for each
            // LogUtil.log(contentBuilder.toString());

            final Chapter chapter = new Chapter();
            chapter.setNovelId(novel.getId());
            chapter.setPageIndex(pageIndex);
            chapter.setOriginUrl(chapterUrl);
            chapter.setTitle(chapterTitle);
            chapter.setContent(contentBuilder.toString());
            chapter.setUpdateTime(System.currentTimeMillis());

            int result = chapterDao.insertChapter(chapter);
            logger.info("insert chapter db result : " + result +"  chapterId :" + chapter.getId());
            logger.info("totalProgress : " + totalProgress);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String findNidFromUrl(final String novelUrl){
        try {
            URL url = new URL(novelUrl);
            String originPath = url.getPath();
            String[] paths = originPath.split("/");
            for(String path : paths){
                if(!StringUtils.isEmpty(path)){
                    return path;
                }
            }//end for each
            return novelUrl;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return novelUrl;
        }
    }

    private void sleepMoment(){
//        try {
//            Thread.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 清洗作者名字数据
     * 目前直接抓到的 会带上 作 者： 字样 把这个去掉
     *
     *
     * @param nameStr
     * @return
     */
    private String authorNameFilter(String nameStr){
        if(nameStr.indexOf(AUTHOR_HEADER) != -1){
            return nameStr.substring(AUTHOR_HEADER.length());
        }
        return nameStr;
    }

    private void prepareNidSets(){
        nidSets.clear();
        List<String> list =  novelDao.selectNids();
        if(list != null){
            logger.info("has nid size : " + list.size());
            nidSets.addAll(list);
        }
    }

}//end class
