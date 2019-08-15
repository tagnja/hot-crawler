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
                checkHotInfoList(hotList);
            }
        }
    }

    public void checkHotInfoList(List<Info> hotList)
    {
        if (hotList != null && ! hotList.isEmpty())
        {
            for (int i = 0; i < hotList.size(); i++)
            {
                Info info = hotList.get(i);
                Assert.assertEquals(String.valueOf(i+1), info.getId());
                Assert.assertNotNull(info.getTitle());
                Assert.assertNotNull(info.getUrl());

                int index1 = info.getUrl().indexOf("http");
                int index2 = info.getUrl().indexOf("http", index1 + 1);
                if (index1 != 0 || index2 != -1)
                {
                    log.error("error url is {}", info.getUrl());
                }
                Assert.assertTrue(index1 == 0);
                Assert.assertTrue(index2 == -1);
            }

        }
    }
}
