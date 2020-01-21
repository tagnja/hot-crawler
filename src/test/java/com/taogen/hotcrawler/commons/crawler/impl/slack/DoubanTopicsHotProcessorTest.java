package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DoubanTopicsHotProcessorTest extends HotProcessorTest {
    @Autowired
    private DoubanTopicsHotProcessor doubanTopicsHotProcessor;

    public DoubanTopicsHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(doubanTopicsHotProcessor.crawlHotList());
    }

}