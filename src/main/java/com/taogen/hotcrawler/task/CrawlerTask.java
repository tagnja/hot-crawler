package com.taogen.hotcrawler.task;

import com.taogen.hotcrawler.crawler.impl.V2exHotProcessor;
import com.taogen.hotcrawler.crawler.impl.ZhihuHotProcessor;
import com.taogen.hotcrawler.entity.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableScheduling
public class CrawlerTask
{
    private static final Logger log = LoggerFactory.getLogger(CrawlerTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private V2exHotProcessor v2exProcess;

    @Autowired
    private ZhihuHotProcessor zhihuProcess;


    @Scheduled(fixedRateString = "${crawlerTask.fixedRate}", initialDelayString = "${crawlerTask.initialDelay}")
    public void crawlerV2ex()
    {
        List<Info> v2exList = v2exProcess.getHotList();
        log.info("crawler v2ex hot list size: " + v2exList.size());
    }

    @Scheduled(fixedRateString = "${crawlerTask.fixedRate}", initialDelayString = "${crawlerTask.initialDelay}")
    public void crawlerZhihu()
    {
        List<Info> zhihuList = zhihuProcess.getHotList();
        log.info("crawler zhihu hot list size: " + zhihuList.size());
    }
}
