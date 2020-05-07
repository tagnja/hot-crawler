package com.taogen.hotcrawler.api.service;

import com.taogen.hotcrawler.commons.entity.Info;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoServiceTest
{
    private static final Logger log = LoggerFactory.getLogger(InfoServiceTest.class);

    @Autowired
    private InfoService infoService;

    @Test
    public void findListByTypeIdTest()
    {
        List<Info> infoList = infoService.findListByTypeId("bilibili");
        log.info("info list size: " + infoList.size());
        log.info("info list : " + infoList);
    }


}
