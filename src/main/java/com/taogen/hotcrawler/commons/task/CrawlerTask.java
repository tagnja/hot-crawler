package com.taogen.hotcrawler.commons.task;

import com.taogen.hotcrawler.api.service.BaseService;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class CrawlerTask
{
    private static final Logger log = LoggerFactory.getLogger(CrawlerTask.class);

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private SiteProperties siteProperties;

    @Value("${crawler.task.enable}")
    private Boolean enable;

    @Value("${crawler.task.threadPoolNum}")
    private int threadPoolNum;

    @Scheduled(fixedRateString = "${crawler.task.fixedRate}", initialDelayString = "${crawler.task.initialDelay}")
    public void crawlHotList()
    {
        if (enable)
        {
            log.info("Crawler task begin...");
            List<SiteProperties.SiteInfo> sites = siteProperties.sites();
            List<SiteProperties.SiteCate> cateList = siteProperties.getCates();
            log.info("site list: {}", sites.size());

            if (cateList != null)
            {
                executeTask(cateList, sites);

            }
        }
    }

    private void executeTask(List<SiteProperties.SiteCate> cateList, List<SiteProperties.SiteInfo> sites)
    {
        threadPoolNum = threadPoolNum < cateList.size() ? threadPoolNum : sites.size();
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolNum);

        for (SiteProperties.SiteCate cate : cateList)
        {
            for (SiteProperties.SiteInfo site : cate.getSites()) {
                executorService.submit(() -> {
                    try {
                        HotProcessor hotProcessor = null;
                        hotProcessor = (HotProcessor) baseService.getBean(site.getProcessorName());
                        List<Info> infoList = hotProcessor.crawlHotList();
                        infoRepository.removeByTypeId(site.getCode());
                        infoRepository.saveAll(infoList, site.getCode());
                    } catch (RuntimeException e) {
                        log.error(e.getMessage(), e);
                    }
                });
            }
        }
    }
}
