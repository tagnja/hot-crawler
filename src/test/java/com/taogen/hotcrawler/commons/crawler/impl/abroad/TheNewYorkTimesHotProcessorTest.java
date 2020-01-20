package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TheNewYorkTimesHotProcessorTest extends HotProcessorTest {
    @Autowired
    private TheNewYorkTimesHotProcessor theNewYorkTimesHotProcessor;

    public TheNewYorkTimesHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(theNewYorkTimesHotProcessor.crawlHotList());
    }
}