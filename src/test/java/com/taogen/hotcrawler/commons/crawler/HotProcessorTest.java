package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;
import org.junit.Ignore;
import org.junit.Test;
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
                Info info = hotList.get(i);
                if (! String.valueOf(i+1).equals(info.getId())){
                    log.debug("error info: {}, {}, {}", info.getId(), info.getTitle(), info.getUrl());
                }
                assertEquals(String.valueOf(i+1), info.getId());
                assertNotNull(info.getTitle());
                assertNotNull(info.getUrl());

                // only single http(s)://
                int index1 = info.getUrl().indexOf("://");
                int index2 = info.getUrl().indexOf("://", index1 + 1);
                if (index2 != -1)
                {
                    log.error("error url is {}", info.getUrl());
                }
                assertTrue(index1 != -1);
                assertTrue(index2 == -1);
            }

        }
    }
}
