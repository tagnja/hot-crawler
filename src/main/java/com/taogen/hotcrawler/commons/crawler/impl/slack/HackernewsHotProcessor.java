package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.stream.CloudmusicHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("HackernewsHotProcessor")
public class HackernewsHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(CloudmusicHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://news.ycombinator.com";
    public static final String HOT_PAGE_URL = "https://news.ycombinator.com";
    public static final String ITEM_KEY = "storylink";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = baseHotProcessor.getDoc(HOT_PAGE_URL, null, log);
        if (doc == null)
        {
            return list;
        }

        // elements
        Elements titleElements = doc.getElementsByClass(ITEM_KEY);
        Elements urlElements = doc.getElementsByClass("subtext");
        log.debug("elements size is {}.", titleElements.size());

        for (int i = 0; i < titleElements.size(); i++)
        {
            // id
            String id = String.valueOf(i+1);

            // url
            Elements aElements = urlElements.get(i).getElementsByTag("a");
            String infoUrl = aElements.get(aElements.size() - 1).attr("href");
            infoUrl = DOMAIN + "/" + infoUrl;

            // title
            String infoTitle = titleElements.get(i).html();

            list.add(new Info(id, infoTitle, infoUrl));
        }

        return baseHotProcessor.handleData(list);
    }
}
