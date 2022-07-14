package xyz.panyi.novel.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.panyi.novel.spider.Spider;
import xyz.panyi.novel.util.LogUtil;

@Component
public class FetchNovelTask {
    private static final Logger logger = LogUtil.buildLogger("FetchNovelTask");

    @Autowired
    private ApplicationContext appContext;

    @Scheduled(fixedDelay = 3 * 24 * 60 * 60 * 1000)
    public void fetch(){
        logger.info("start fetch novel data");

        final Spider spider = appContext.getBean(Spider.class);
        logger.info("create spider instance : " + spider);

        logger.info("fetch novel data ended!");
    }
}
