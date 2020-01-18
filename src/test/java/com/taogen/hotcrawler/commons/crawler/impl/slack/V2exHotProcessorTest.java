package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class V2exHotProcessorTest extends HotProcessorTest {
    @Autowired
    private V2exHotProcessor v2exHotProcessor;

    public V2exHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(v2exHotProcessor.crawlHotList());
    }
}