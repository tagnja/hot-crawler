package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.api.service.BaseService;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.DocumentHotProcessor;
import com.taogen.hotcrawler.commons.crawler.handler.HandlerCenter;
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

@Component("DoubanHotProcessor")
public class DoubanHotProcessor extends DocumentHotProcessor
{
    public static final String ITEM_KEY = "channel-item";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(DoubanHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.GET;
    }

    @Override
    protected Elements getElements(Document document) {
        Elements elements = document.getElementsByClass(ITEM_KEY);
        return elements;
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> list = new ArrayList<>();
        if (elements != null) {
            int i = 0;
            for (Element element : elements) {
                Element itemElement = null;
                try {
                    itemElement = element.getElementsByClass("bd").get(0).getElementsByTag("a").get(0);
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    log.error("Can't found item element by attribute!", e);
                    continue;
                }
                String id = String.valueOf(++i);
                String infoUrl = itemElement.attr("href");
                String infoTitle = itemElement.html();
                list.add(new Info(id, infoTitle, infoUrl));
            }
        }
        return handlerCenter.handleData(list);
    }

    @Override
    protected Map<String, String> generateHeader() {
        return null;
    }

    @Override
    protected String generateRequestBody() {
        return null;
    }
}
