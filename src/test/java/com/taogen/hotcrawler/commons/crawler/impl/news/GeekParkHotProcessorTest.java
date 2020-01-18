package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GeekParkHotProcessorTest extends HotProcessorTest {
    @Autowired
    private GeekParkHotProcessor geekParkHotProcessor;

    public GeekParkHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(geekParkHotProcessor.crawlHotList());
    }

}