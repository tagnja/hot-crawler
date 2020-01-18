package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class JavaWorldHotProcessorTest extends HotProcessorTest {

    @Autowired
    private JavaWorldHotProcessor javaWorldHotProcessor;

    public JavaWorldHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(javaWorldHotProcessor.crawlHotList());
    }
}