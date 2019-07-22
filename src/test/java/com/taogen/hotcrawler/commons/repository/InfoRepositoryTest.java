package com.taogen.hotcrawler.commons.repository;

import com.taogen.hotcrawler.commons.entity.db.Info;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoRepositoryTest
{
    private Logger log = LoggerFactory.getLogger(InfoRepositoryTest.class);
    @Autowired
    private InfoRepository infoRepository;

    @Test
    public void saveTest()
    {
        infoRepository.save(new Info("1", "haha", "http://bvaidu"), "1");
        infoRepository.save(new Info("2", "haha2", "http://bvaidu22"), "1");
        Assert.assertNotNull(infoRepository.findByInfoId("1", "1"));
        Assert.assertNotNull(infoRepository.findByInfoId("1", "2"));
    }

    @Test
    public void saveAllTest()
    {
        List infoList = Arrays.asList(new Info("11", "11", "http://bvaidu"), new Info("22", "22", "http://bvaidu"));
        infoRepository.removeByTypeId("1");
        infoRepository.saveAll(infoList, "1");
        Assert.assertNotNull(infoRepository.findByInfoId("1", "11"));
        Assert.assertNotNull(infoRepository.findByInfoId("1", "22"));
    }
    @Test
    public void countByTypeIdTest()
    {
        long count = infoRepository.countByTypeId("1");
        log.info("count: "+ count);
    }

    @Test
    public void findByTypeIdTest()
    {
        log.info("list: " + infoRepository.findByTypeId("1"));
    }

    @Test
    public void findByInfoIdTest()
    {
        log.info("item: " + infoRepository.findByInfoId("1", "1"));
    }

    @Test
    public void remveByTypeId()
    {
        log.info("before remove: " + infoRepository.findByTypeId("1"));
        infoRepository.removeByTypeId("1");
        log.info("after remove: " + infoRepository.findByTypeId("1"));
    }


}
