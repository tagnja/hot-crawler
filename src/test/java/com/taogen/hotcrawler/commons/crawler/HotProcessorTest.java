package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.config.SiteProperties;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

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
        List<SiteProperties.SiteInfo> siteInfos = siteProperties.sites();
        log.info("site list: " + siteInfos);
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
                if (hotList != null && ! hotList.isEmpty())
                {
                    log.info("crawl {} hot list size is {}", site.getName(), hotList.size());
                    Info info = hotList.get(0);
                    log.debug("first item is {}", info);
                    Assert.assertNotNull(info.getTitle());
                    Assert.assertNotNull(info.getUrl());
                    int index1 = info.getUrl().indexOf("http");
                    int index2 = info.getUrl().indexOf("http", index1 + 1);
                    Assert.assertTrue(index1 == 0);
                    Assert.assertTrue(index2 == -1);
                }
                else
                {
                    log.info("crawl {} hot list is null", site.getName());
                }
            }
        }
    }
}
