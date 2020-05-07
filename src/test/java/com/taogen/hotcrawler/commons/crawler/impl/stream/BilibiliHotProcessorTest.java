package com.taogen.hotcrawler.commons.crawler.impl.stream;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BilibiliHotProcessorTest extends HotProcessorTest {
    @Autowired
    private BilibiliHotProcessor bilibiliHotProcessor;

    public BilibiliHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(bilibiliHotProcessor.crawlHotList());
    }
}