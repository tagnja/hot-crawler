package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BBCNewsHotProcessorTest extends HotProcessorTest {

    @Autowired
    private BBCNewsHotProcessor bbcNewsHotProcessor;

    public BBCNewsHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(bbcNewsHotProcessor.crawlHotList());
    }
}