package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("GithubHotProcessor")
public class GithubHotProcessor implements HotProcessor
{
    @Value("${sites[2].id}")
    private  String SITE_ID;

    @Value("${sites[2].domain}")
    private String DOMAIN;

    @Value("${sites[2].hotPageUrl}")
    private String HOT_PAGE_URL;

    @Value("${sites[2].itemKey}")
    private String ITEM_KEY;

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = null;
        try {
            doc = Jsoup.connect(HOT_PAGE_URL).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // elements
        Elements elements = doc.getElementsByClass(ITEM_KEY);

        int i = 0;
        for (Element element : elements)
        {
            // id
            String id = String.valueOf(++i);

            // url
            Element urlElement = element.getElementsByTag("h1").get(0).getElementsByTag("a").get(0);
            String infoUrl = urlElement.attr("href");

            // title
            Element titleElement = element.getElementsByTag("p").get(0);
            String infoTitle = infoUrl.substring(infoUrl.indexOf("/",1) + 1) + ". " + titleElement.html();

            infoUrl = DOMAIN + infoUrl;
            list.add(new Info(id, infoTitle, infoUrl));
        }
        return list;
    }
}
