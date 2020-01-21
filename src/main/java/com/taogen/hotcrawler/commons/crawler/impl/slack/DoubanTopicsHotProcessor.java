package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.DocumentHotProcessor;
import com.taogen.hotcrawler.commons.crawler.SimpleDocumentHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoubanTopicsHotProcessor extends DocumentHotProcessor {
    public static final String ITEM_KEY = "indexs";

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
    protected Elements getElements(Document document) {
        return document.getElementsByClass("trend").get(0).getElementsByTag("li");
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> infoList = new ArrayList<>();
        for (Element element : elements){
            String infoTitle = element.getElementsByTag("a").get(0).text();
            String infoUrl = element.getElementsByTag("a").get(0).attr("href");
            infoList.add(new Info(infoTitle, infoUrl));
        }
        return infoList;
    }
}
