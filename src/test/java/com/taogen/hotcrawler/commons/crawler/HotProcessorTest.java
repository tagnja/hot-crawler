package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.api.service.BaseService;
import com.taogen.hotcrawler.commons.entity.Info;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotProcessorTest
{
    Logger log = LoggerFactory.getLogger(HotProcessorTest.class);

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private BaseService baseService;

    @Test
    public void getHotInfoTest()
    {
        List<SiteProperties.SiteInfo> siteInfos = siteProperties.getSites();

        if (siteInfos != null)
        {
            for (SiteProperties.SiteInfo site : siteInfos)
            {
                HotProcessor hotProcessor = null;
                try
                {
                    hotProcessor = (HotProcessor) baseService.getBean(site.getProcessorName());
                }
                catch (BeansException e)
                {
                    log.error(e.getMessage());
                    continue;
                }
                List<Info> hotList = hotProcessor.crawlHotList();
                Assert.assertNotNull(hotList);
                log.info("crawl " + site.getName() + " hot list size: " + hotList.size());
                Assert.assertTrue(hotList.size() > 0);
            }
        }
    }
}
