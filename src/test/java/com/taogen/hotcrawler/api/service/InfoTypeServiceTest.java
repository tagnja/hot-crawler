package com.taogen.hotcrawler.api.service;

import com.taogen.hotcrawler.commons.entity.db.InfoType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoTypeServiceTest
{
    private static final Logger log = LoggerFactory.getLogger(InfoTypeServiceTest.class);

    @Autowired
    private InfoTypeService infoTypeService;

    @Test
    public void finaAllTest()
    {
        log.info("before: " + infoTypeService.findAll().toString());
        infoTypeService.save(new InfoType("11", "v2ex"));
        infoTypeService.save(new InfoType("22", "zhihu"));
        infoTypeService.save(new InfoType("33", "douban"));
        log.info("after: " + infoTypeService.findAll().toString());
    }

    @Test
    public void saveTest()
    {
        infoTypeService.save(new InfoType("1", "zhihu"));
        Assert.assertNotNull(infoTypeService.findById("1"));
    }

    @Test
    public void removeTest()
    {
        infoTypeService.save(new InfoType("2", "v2ex"));
        Assert.assertNotNull(infoTypeService.findById("2"));
        infoTypeService.remove("2");
        Assert.assertNull(infoTypeService.findById("2"));
    }
}
