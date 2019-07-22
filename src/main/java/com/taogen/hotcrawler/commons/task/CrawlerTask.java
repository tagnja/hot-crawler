package com.taogen.hotcrawler.commons.task;

import com.taogen.hotcrawler.commons.crawler.impl.V2exHotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.ZhihuHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
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

    @Autowired
    private InfoRepository infoRepository;


    @Scheduled(fixedRateString = "${crawler.task.fixedRate}", initialDelayString = "${crawler.task.initialDelay}")
    public void crawlerV2ex()
    {
        List<Info> v2exList = v2exProcess.crawlHotList();
        log.info("crawler v2ex hot list size: " + v2exList.size());
        infoRepository.removeByTypeId(v2exProcess.getSITE_ID());
        infoRepository.saveAll(v2exList, v2exProcess.getSITE_ID());
    }

    @Scheduled(fixedRateString = "${crawler.task.fixedRate}", initialDelayString = "${crawler.task.initialDelay}")
    public void crawlerZhihu()
    {
        List<Info> zhihuList = zhihuProcess.crawlHotList();
        log.info("crawler zhihu hot list size: " + zhihuList.size());
        infoRepository.removeByTypeId(zhihuProcess.getSITE_ID());
        infoRepository.saveAll(zhihuList, zhihuProcess.getSITE_ID());
    }
}