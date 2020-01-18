package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class JuejinHotProcessorTest extends HotProcessorTest {
    @Autowired
    private JuejinHotProcessor juejinHotProcessor;

    public JuejinHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(juejinHotProcessor.crawlHotList());
    }
}