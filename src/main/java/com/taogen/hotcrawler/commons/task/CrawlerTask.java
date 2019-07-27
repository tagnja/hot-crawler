package com.taogen.hotcrawler.commons.task;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.api.service.BaseService;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Configuration
@EnableScheduling
public class CrawlerTask
{
    private static final Logger log = LoggerFactory.getLogger(CrawlerTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private SiteProperties siteProperties;

    @Value("${crawler.task.enable}")
    private Boolean ENABLE;

    @Value("${crawler.task.threadPoolNum}")
    private int THREAD_POOL_NUM;

    @Scheduled(fixedRateString = "${crawler.task.fixedRate}", initialDelayString = "${crawler.task.initialDelay}")
    public void crawlHotList()
    {
        if (ENABLE)
        {
            log.info("Crawler task begin...");
            List<SiteProperties.SiteInfo> sites = siteProperties.sites();
            List<SiteProperties.SiteCate> cateList = siteProperties.getCates();
            log.info("site list: " + cateList.toString());

            if (cateList != null)
            {
                int threadPoolNum = THREAD_POOL_NUM < cateList.size() ? THREAD_POOL_NUM : sites.size();
                ExecutorService executorService = Executors.newFixedThreadPool(threadPoolNum);

                for (SiteProperties.SiteCate cate : cateList)
                {
                    for (SiteProperties.SiteInfo site : cate.getSites()) {
                        executorService.submit(() -> {
                            HotProcessor hotProcessor = null;
                            try {
                                hotProcessor = (HotProcessor) baseService.getBean(site.getProcessorName());
                            } catch (BeansException e) {
                                log.error(e.getMessage());
                                return;
                            }
                            List<Info> infoList = hotProcessor.crawlHotList();
                            log.info("crawler " + site.getName() + " hot list size: " + infoList.size());
                            infoRepository.removeByTypeId(cate.getId(), site.getId());
                            infoRepository.saveAll(infoList, cate.getId(), site.getId());
                        });
                    }
                }
            }
        }
    }
}
