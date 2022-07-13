package xyz.panyi.novel.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import xyz.panyi.novel.util.LogUtil;

import java.io.IOException;

/**
 * 爬虫
 */
public class Spider {
    private static final Logger logger = LogUtil.buildLogger("Spider");

    private static final String AUTHOR_HEADER = "作 者：";

    private static final String P_HEADER = "  ";

    /**
     *  抓取小说内容
     * @param novelUrl
     */
    public void fetchNovel(final String novelUrl){
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

            LogUtil.log("chapter count : " + chs.size());
            for(int i = 0 ; i < chs.size();i++){
                Element element = chs.get(i);

                final String chapterUrl = element.absUrl("href");
                final String chapterTitle = element.text();
                LogUtil.log("chapter title: " + chapterTitle);
                LogUtil.log("href:" + chapterUrl);

                LogUtil.log("chapter catch "+i  +" / " + chs.size());
                fetchChapter(chapterUrl);

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
    public void fetchChapter(final String chapterUrl){
        try {
            Document doc = Jsoup.connect(chapterUrl).get();
            Elements ps = doc.getElementById("article").getElementsByTag("p");

            StringBuffer contentBuilder = new StringBuffer();
            for(Element e : ps){
                contentBuilder.append(P_HEADER).append(e.text()).append("\n");
            }//end for each
            LogUtil.log(contentBuilder.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sleepMoment(){
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private String authorNameFilter(String nameStr){
        if(nameStr.indexOf(AUTHOR_HEADER) != -1){
            return nameStr.substring(AUTHOR_HEADER.length());
        }
        return nameStr;
    }
}
