package com.taogen.hotcrawler.commons.crawler.impl.news;

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

@Component("NatureHotProcessor")
public class NatureHotProcessor extends DocumentHotProcessor
{
    public static final String ITEM_KEY = "c-article-item__wrapper";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(NatureHotProcessor.class);
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
                try {
                    String infoTitle = element.getElementsByClass("c-article-item__title").get(0).html();
                    StringBuilder infoUrl = new StringBuilder();
                    infoUrl.append(getDomainByUrl(this.url));
                    infoUrl.append(element.getElementsByTag("a").get(0).attr("href"));
                    String id = String.valueOf(++i);
                    list.add(new Info(id, infoTitle, infoUrl.toString()));
                } catch (IndexOutOfBoundsException e) {
                    log.error("Item {} can't find attribute!", i, e);
                }
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
