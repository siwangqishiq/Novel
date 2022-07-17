package xyz.panyi.novel.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.panyi.novel.util.LogUtil;

/**
 * 抓取任务
 *
 */
@Component
public class FetchNovelTask {
    private static final Logger logger = LogUtil.buildLogger("FetchNovelTask");

    @Autowired
    private ApplicationContext appContext;

    @Scheduled(fixedDelay = 1 * 60 * 60 * 1000)
    public void fetch(){
        logger.info("start fetch novel data");

        final Spider spider = appContext.getBean(Spider.class);
        logger.info("create spider instance : " + spider);
        spider.parseComposePage();
        logger.info("fetch novel data ended!");
    }
}
