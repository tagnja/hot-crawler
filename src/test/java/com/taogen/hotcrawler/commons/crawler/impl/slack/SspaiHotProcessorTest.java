package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import com.taogen.hotcrawler.commons.entity.Info;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SspaiHotProcessorTest extends HotProcessorTest {

    @Autowired
    private SspaiHotProcessor sspaiHotProcessor;

    public SspaiHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        List<Info> infoList = sspaiHotProcessor.crawlHotList();
        log.debug(infoList.toString());
        checkHotInfoList(infoList);
    }
}