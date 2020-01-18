package com.taogen.hotcrawler.commons.crawler.impl.slack;

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

@Component("TianyaHotProcessor")
public class TianyaHotProcessor extends SimpleDocumentHotProcessor
{
    public static final String ITEM_KEY = "td-title";

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
        element = element.getElementsByTag("a").get(0);
        StringBuilder infoUrl = new StringBuilder();
        infoUrl.append(this.prefix);
        infoUrl.append(element.attr("href"));
        String infoTitle = element.html();
        Info info = new Info();
        info.setTitle(infoTitle);
        info.setUrl(infoUrl.toString());
        return info;
    }
}
