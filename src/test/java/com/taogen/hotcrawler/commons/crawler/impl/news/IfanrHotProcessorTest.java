package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class IfanrHotProcessorTest extends HotProcessorTest {

    @Autowired
    private IfanrHotProcessor ifanrHotProcessor;

    public IfanrHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(ifanrHotProcessor.crawlHotList());
    }
}