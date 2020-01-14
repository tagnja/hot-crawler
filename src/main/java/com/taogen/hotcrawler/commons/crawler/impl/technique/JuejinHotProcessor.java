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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("JuejinHotProcessor")
public class JuejinHotProcessor extends APIHotProcessor
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
        this.log = LoggerFactory.getLogger(JuejinHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.POST;
    }

    @Override
    protected List<Info> getInfoDataByJson(String json) {
        List<Info> list = new ArrayList<>();
        if (json != null && json.length() > 0)
        {
            List<String> titles = JsonPath.read(json, "$.data.articleFeed.items.edges.[*].node.title");
            List<String> urls = JsonPath.read(json, "$.data.articleFeed.items.edges.[*].node.originalUrl");
            List<Info> indexInfoList = getInfoListByTitlesAndUrls(titles, urls);
            list.addAll(indexInfoList);
            log.debug("index infoList size is {}", indexInfoList.size());
        }
        return list;
    }

    @Override
    protected Map<String, String> generateHeader() {
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Host", "web-api.juejin.im");
        header.put("Origin","https://juejin.im");
        header.put("Referer", "https://juejin.im/?sort=popular");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        header.put("X-Agent", "Juejin/Web");
        return header;
    }

    @Override
    protected String generateRequestBody() {
        return "{\"operationName\":\"\",\"query\":\"\",\"variables\":{\"first\":20,\"after\":\"\",\"order\":\"POPULAR\"},\"extensions\":{\"query\":{\"id\":\"21207e9ddb1de777adeaca7a2fb38030\"}}}";
    }
}
