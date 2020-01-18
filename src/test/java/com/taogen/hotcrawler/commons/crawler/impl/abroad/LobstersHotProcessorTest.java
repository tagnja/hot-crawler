package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LobstersHotProcessorTest extends HotProcessorTest {

    @Autowired
    private LobstersHotProcessor lobstersHotProcessor;

    public LobstersHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(lobstersHotProcessor.crawlHotList());
    }

}