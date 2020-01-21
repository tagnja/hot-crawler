package com.taogen.hotcrawler.commons.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SitePropertiesTest {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SiteProperties siteProperties;

    @Test
    public void argumentsCheck(){
        log.debug("begin argument check in property file...");
        // Is url right? Does processName exist?
        if (siteProperties.cates != null){
            for (SiteProperties.SiteCate siteCate : siteProperties.cates){
                if (siteCate.getSites() != null){
                    for (SiteProperties.SiteInfo siteInfo : siteCate.getSites()){
                        Object object = getObjectByClassPath(siteInfo.getProcessorClassPath());
                        if(object == null) {
                            StringBuilder errorMessage = new StringBuilder();
                            errorMessage.append(siteInfo.getName());
                            errorMessage.append("'s processorClassPath configuration is error in sites.properties!");
                            log.error(errorMessage.toString());
                        }
                        assertNotNull(object);
                    }
                }
            }
        }
    }

    private Object getObjectByClassPath(String processorClassPath) {
        Object object = null;
        try {
            object = applicationContext.getBean(Class.forName(processorClassPath));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return object;
    }
}