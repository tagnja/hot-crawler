package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class InfoqHotProcessorTest extends HotProcessorTest {

    @Autowired
    private InfoqHotProcessor infoqHotProcessor;
    public InfoqHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(infoqHotProcessor.crawlHotList());
    }
}