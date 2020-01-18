package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ZhihuHotProcessorTest extends HotProcessorTest {

    @Autowired
    private ZhihuHotProcessor zhihuHotProcessor;

    public ZhihuHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(zhihuHotProcessor.crawlHotList());
    }
}