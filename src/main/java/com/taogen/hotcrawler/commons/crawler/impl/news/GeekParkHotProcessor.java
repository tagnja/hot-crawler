package com.taogen.hotcrawler.commons.crawler.impl.news;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.SimpleDocumentHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("GeekParkHotProcessor")
public class GeekParkHotProcessor extends SimpleDocumentHotProcessor
{
    public static final String ITEM_KEY = "article-item";

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
        this.elementClass = ITEM_KEY;
    }

    @Override
    protected Info getInfoByElement(Element element) {
        Element item = element.getElementsByClass("img-cover-wrap").get(0);
        String infoTitle = item.attr("data-event-label");
        int index = infoTitle.indexOf("/news");
        if (index != -1) {
            infoTitle = infoTitle.substring(0, index);
        }
        StringBuilder infoUrl = new StringBuilder();
        infoUrl.append(this.prefix);
        infoUrl.append(item.attr("href"));
        Info info = new Info();
        info.setTitle(infoTitle);
        info.setUrl(infoUrl.toString());
        return info;
    }

}
