package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.api.service.BaseService;
import com.taogen.hotcrawler.commons.crawler.impl.V2exHotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.ZhihuHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotProcessorTest
{
    Logger log = LoggerFactory.getLogger(HotProcessorTest.class);

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private BaseService baseService;

    @Value("${crawler.task.threadPoolNum}")
    private int THREAD_POOL_NUM;

    @Test
    public void getHotInfoTest()
    {
        List<SiteProperties.SiteInfo> siteInfos = siteProperties.getSites();

        if (siteInfos != null)
        {
            int threadPoolNum = THREAD_POOL_NUM < siteInfos.size() ? THREAD_POOL_NUM : siteInfos.size();
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolNum);
            for (SiteProperties.SiteInfo site : siteInfos)
            {
                executorService.submit(() -> {
                    HotProcessor hotProcessor = (HotProcessor) baseService.getBean(site.getProcessorName());
                    List<Info> hotList = hotProcessor.crawlHotList();
                    Assert.assertNotNull(hotList);
                    log.info("crawl " + site.getName() + " hot list size: " + hotList.size());
                    Assert.assertTrue(hotList.size() > 0);
                });
            }
        }

    }
}
