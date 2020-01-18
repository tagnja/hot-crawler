package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EconomistHotProcessorTest extends HotProcessorTest {

    @Autowired
    private EconomistHotProcessor economistHotProcessor;

    public EconomistHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(economistHotProcessor.crawlHotList());
    }
}