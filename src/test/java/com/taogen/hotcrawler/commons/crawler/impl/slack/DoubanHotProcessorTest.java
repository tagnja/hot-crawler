package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DoubanHotProcessorTest extends HotProcessorTest {

    @Autowired
    private DoubanHotProcessor doubanHotProcessor;

    public DoubanHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(doubanHotProcessor.crawlHotList());
    }
}