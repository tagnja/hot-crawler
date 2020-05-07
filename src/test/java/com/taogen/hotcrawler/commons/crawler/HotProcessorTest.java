package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class HotProcessorTest
{
    protected Logger log;

    public void checkHotInfoList(List<Info> hotList)
    {
        if (hotList != null && ! hotList.isEmpty())
        {
            for (int i = 0; i < hotList.size(); i++)
            {
                testInfo(hotList.get(i), i);
                log.debug(hotList.get(i).getMap().toString());
            }

        }
    }

    private void testInfo(Info info, int i) {
        testId(String.valueOf(i+1), info.getId());
        testTitle(info.getTitle());
        testUrl(info.getUrl());
    }

    private void testId(String expectId, String actualId){
        assertEquals(expectId, actualId);
    }

    private void testTitle(String title) {
        assertNotNull(title);
    }

    private void testUrl(String url) {
        assertNotNull(url);
        assertTrue(url.startsWith("http"));
        hasOnlySingleUrl(url);

    }

    private void hasOnlySingleUrl(String url){
        int index1 = url.indexOf("://");
        int index2 = url.indexOf("://", index1 + 1);
        if (index2 != -1)
        {
            log.error("error url is {}", url);
        }
        assertTrue(index1 != -1);
        assertTrue(index2 == -1);
    }
}
