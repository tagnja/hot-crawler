package com.taogen.hotcrawler.commons.crawler.impl.slack;

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
import java.util.Map;

@Component("TianyaHotProcessor")
public class TianyaHotProcessor extends DocumentHotProcessor
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
    }

    @Override
    protected Elements getElements(Document document) {
        return document.getElementsByClass(ITEM_KEY);
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> list = new ArrayList<>();
        if (elements != null) {
            int i = 0;
            for (Element element : elements) {
                Element itemElement = null;
                try {
                    itemElement = element.getElementsByTag("a").get(0);
                } catch (IndexOutOfBoundsException e) {
                    log.error("Can't found item element by attribute!", e);
                    continue;
                }
                String id = String.valueOf(++i);
                String infoUrl = itemElement.attr("href");
                String infoTitle = itemElement.html();
                infoUrl = this.prefix + infoUrl;
                list.add(new Info(id, infoTitle, infoUrl));
            }
        }
        return list;
    }
}
