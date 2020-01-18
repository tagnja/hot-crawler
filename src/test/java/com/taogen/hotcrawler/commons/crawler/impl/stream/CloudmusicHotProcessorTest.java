package com.taogen.hotcrawler.commons.crawler.impl.stream;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CloudmusicHotProcessorTest extends HotProcessorTest {
    @Autowired
    private CloudmusicHotProcessor cloudmusicHotProcessor;

    public CloudmusicHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(cloudmusicHotProcessor.crawlHotList());
    }
}