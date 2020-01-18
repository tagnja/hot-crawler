package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class NatureHotProcessorTest extends HotProcessorTest {

    @Autowired
    private NatureHotProcessor natureHotProcessor;
    public NatureHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(natureHotProcessor.crawlHotList());
    }
}