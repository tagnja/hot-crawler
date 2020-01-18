package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DzoneHotProcessorTest extends HotProcessorTest {

    @Autowired
    private DzoneHotProcessor dzoneHotProcessor;

    public DzoneHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(dzoneHotProcessor.crawlHotList());
    }
}