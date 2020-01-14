package com.taogen.hotcrawler.commons.crawler.impl.stream;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("CloudmusicHotProcessor")
public class CloudmusicHotProcessor extends DocumentHotProcessor
{
    public static final String ITEM_KEY = "song-list-pre-cache";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(CloudmusicHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.GET;
    }

    @Override
    protected Elements getElements(Document document) {
        Elements elements = null;
        Element contentElement = document.getElementById(ITEM_KEY);
        if (contentElement != null)
        {
            elements = contentElement.getElementsByTag("li");
        }
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
                    itemElement = element.getElementsByTag("a").get(0);
                    String id = String.valueOf(++i);
                    StringBuilder infoUrl = new StringBuilder();
                    infoUrl.append(this.prefix);
                    infoUrl.append("#");
                    infoUrl.append(itemElement.attr("href"));
                    String infoTitle = itemElement.html();
                    list.add(new Info(id, infoTitle, infoUrl.toString()));
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    log.error("Can't found item element by attribute!", e);
                    continue;
                }
            }
        }
        return list;
    }

    @Override
    protected Map<String, String> generateHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Host", "music.163.com");
        header.put("Referer", "https://music.163.com/");
        return header;
    }

    @Override
    protected String generateRequestBody() {
        return null;
    }
}
