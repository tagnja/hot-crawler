package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.crawler.HotProcessorTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SegmentFaultHotProcessorTest extends HotProcessorTest {
    @Autowired
    private SegmentFaultHotProcessor segmentFaultHotProcessor;

    public SegmentFaultHotProcessorTest(){
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void crawlHotList() {
        checkHotInfoList(segmentFaultHotProcessor.crawlHotList());
    }
}