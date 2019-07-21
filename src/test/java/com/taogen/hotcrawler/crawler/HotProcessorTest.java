package com.taogen.hotcrawler.crawler;

import com.taogen.hotcrawler.crawler.impl.V2exHotProcessor;
import com.taogen.hotcrawler.crawler.impl.ZhihuHotProcessor;
import com.taogen.hotcrawler.entity.Info;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HotProcessorTest
{
    @Test
    public void getV2exHotInfo()
    {
        HotProcess v2ex = new V2exHotProcessor();
        List<Info> v2exList = v2ex.getHotList();
        Assert.assertNotNull(v2exList);
        Assert.assertTrue(v2exList.size() > 0);
        System.out.println("v2ex hot list size: " + v2exList.size());

    }

    @Test
    public void getZhihuHotInfo()
    {
        HotProcess zhihu = new ZhihuHotProcessor();
        List<Info> zhihuList = zhihu.getHotList();
        Assert.assertNotNull(zhihuList);
        Assert.assertTrue(zhihuList.size() > 0);
        System.out.println("zhihu hot list size: " + zhihuList.size());
    }
}
