package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("GithubHotProcessor")
public class GithubHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(GithubHotProcessor.class);

    @Autowired
    private SiteProperties siteProperties;

    private String DOMAIN;
    private String HOT_PAGE_URL;
    private String ITEM_KEY;

    @PostConstruct
    public void init()
    {
        SiteProperties.SiteInfo siteInfo = siteProperties.findByProcessorName(this.getClass().getSimpleName());
        this.DOMAIN = siteInfo.getDomain();
        this.HOT_PAGE_URL = siteInfo.getHotPageUrl();
        this.ITEM_KEY = siteInfo.getItemKey();
    }

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = null;
        try {
            // Fix jsoup SocketTimeoutException: Read timeout. Add a timeout.
            doc = Jsoup.connect(HOT_PAGE_URL).timeout(10 * 1000).get();

        } catch (IOException e) {
            log.error(e.getMessage());
            log.error("Fail to connect!");
            return list;
        }

        log.debug("Title: " + doc.title());

        // elements
        Elements elements = doc.getElementsByClass(ITEM_KEY);

        try
        {
            int i = 0;
            for (Element element : elements)
            {
                log.debug("itemElement: " + element);
                // id
                String id = String.valueOf(++i);

                // url
                Element urlElement = element.getElementsByTag("h1").get(0).getElementsByTag("a").get(0);
                String infoUrl = urlElement.attr("href");

                // title
                Element titleElement = element.getElementsByTag("p").get(0);
                String infoTitle = infoUrl.substring(infoUrl.indexOf("/", 1) + 1) + ". " + titleElement.html();

                infoUrl = DOMAIN + infoUrl;
                list.add(new Info(id, infoTitle, infoUrl));
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            log.error(e.getMessage());
        }
        return list;
    }
}
