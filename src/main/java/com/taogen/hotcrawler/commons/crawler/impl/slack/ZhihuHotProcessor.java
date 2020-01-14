package com.taogen.hotcrawler.commons.crawler.impl.slack;

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

@Component("ZhihuHotProcessor")
public class ZhihuHotProcessor extends APIHotProcessor
{
    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(ZhihuHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.GET;
    }

    @Override
    protected Map<String, String> generateHeader() {
        // not need
        return null;
    }

    @Override
    protected String generateRequestBody() {
        // not need
        return null;
    }

    @Override
    public List<Info> getInfoDataByJson(String json) {
        List<Info> list = new ArrayList<>();
        if (json == null)
        {
            return list;
        }

        List<String> titles = JsonPath.read(json, "$.data.[*].target.title");
        List<String> urls = JsonPath.read(json, "$.data.[*].target.url");

        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, urls.get(i).replace("https://api.zhihu.com/questions", getDomainByUrl(this.url) + "/question"));
        }

        for (int i = 1; i < titles.size(); i++)
        {
            list.add(new Info(String.valueOf(i), titles.get(i), urls.get(i)));
        }
        return list;
    }
}
