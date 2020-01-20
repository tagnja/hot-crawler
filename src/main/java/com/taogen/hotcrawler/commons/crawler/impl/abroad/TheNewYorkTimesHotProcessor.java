package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.DocumentHotProcessor;
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

@Component("TheNewYorkTimesHotProcessor")
public class TheNewYorkTimesHotProcessor extends DocumentHotProcessor {

    public static final String FIRST_ITEM_KEY = "css-2pep1h";
    public static final String OTHER_ITEM_KEY = "css-1iski2w";

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
        Elements firstElements = document.getElementsByClass(FIRST_ITEM_KEY);
        Elements otherElements = document.getElementsByClass(OTHER_ITEM_KEY);
        Elements resultElements = new Elements();
        resultElements.addAll(firstElements);
        resultElements.addAll(otherElements);
        return resultElements;
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> infoList = new ArrayList<>();
        String firstTitle = elements.get(0).getElementsByTag("a").attr("href");
        String firstUrl = elements.get(0).getElementsByClass("css-x01ngn").get(0).html();
        infoList.add(new Info(firstTitle, firstUrl));
        for (int i = 1; i < elements.size(); i++){
            Element element = elements.get(i).getElementsByTag("a").get(0);
            String infoUrl = element.attr("href");
            String infoTitle = element.getElementsByClass("css-14kabif").get(0).html();
            infoList.add(new Info(infoTitle, infoUrl));
        }
        return infoList;
    }
}
