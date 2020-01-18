package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class HackernewsHotProcessorTest extends HotProcessorTest {

    @Autowired
    private HackernewsHotProcessor hackernewsHotProcessor;

    public HackernewsHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(hackernewsHotProcessor.crawlHotList());
    }
}