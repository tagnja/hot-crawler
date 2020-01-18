package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.APIHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("DzoneHotProcessor")
public class DzoneHotProcessor extends APIHotProcessor
{
    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        RequestMethod requestMethod = RequestMethod.GET;
        setFieldsByProperties(siteProperties, requestMethod, generateHeader(),generateRequestBody());
        injectBeansByContext(context);
        setLog(LoggerFactory.getLogger(getClass()));
    }

    @Override
    protected List<Info> getInfoDataByJson(String json) {
        List<Info> list = new ArrayList<>();
        if (json != null && json.length() > 0) {
            List<String> titles = JsonPath.read(json, "$.result.data.nodes.[*].title");
            List<String> urls = JsonPath.read(json, "$.result.data.nodes.[*].articleLink");
            urls = urlsAddPrefix(this.prefix, urls);
            List<Info> indexInfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(indexInfoList);
            log.debug("index infoList size is {}", indexInfoList.size());
        }
        return list;
    }
}
