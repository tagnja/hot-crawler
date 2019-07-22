package com.taogen.hotcrawler.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        infoService.findListByTypeId("1");
    }


}