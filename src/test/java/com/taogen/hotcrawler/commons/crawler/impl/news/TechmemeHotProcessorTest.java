package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import com.taogen.hotcrawler.commons.crawler.impl.abroad.TechmemeHotProcessor;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TechmemeHotProcessorTest extends HotProcessorTest {

    @Autowired
    private TechmemeHotProcessor techmemeHotProcessor;

    public TechmemeHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(techmemeHotProcessor.crawlHotList());
    }
}