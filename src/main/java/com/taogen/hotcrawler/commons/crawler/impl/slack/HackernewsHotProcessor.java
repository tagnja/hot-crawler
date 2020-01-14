package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.DocumentHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("HackernewsHotProcessor")
public class HackernewsHotProcessor extends DocumentHotProcessor
{
    private static final String ITEM_KEY = "storylink";
    private Elements titleElements;
    private Elements urlElements;

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(HackernewsHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.GET;
    }

    @Override
    protected Elements getElements(Document document) {
        this.titleElements = document.getElementsByClass(ITEM_KEY);
        this.urlElements = document.getElementsByClass("subtext");
        return null;
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> list = new ArrayList<>();
        if (this.titleElements != null && this.urlElements != null) {
            for (int i = 0; i < titleElements.size(); i++) {
                String id = String.valueOf(i + 1);
                Elements aElements = urlElements.get(i).getElementsByTag("a");
                String infoUrl = aElements.get(aElements.size() - 1).attr("href");
                infoUrl = this.prefix + "/" + infoUrl;
                String infoTitle = titleElements.get(i).html();
                list.add(new Info(id, infoTitle, infoUrl));
            }
        }
        return list;
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
