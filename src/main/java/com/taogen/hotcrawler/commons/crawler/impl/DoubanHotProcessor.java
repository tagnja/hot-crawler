package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("DoubanHotProcessor")
public class DoubanHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(DoubanHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String HOT_PAGE_URL = "https://www.douban.com/group/explore";
    public static final String ITEM_KEY = "channel-item";

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
        Elements elements = doc.getElementsByClass(ITEM_KEY);
        log.debug("elements size is {}", elements.size());

        int i = 0;
        for (Element element : elements)
        {
            Element itemElement = null;
            try
            {
                itemElement = element.getElementsByClass("bd").get(0).getElementsByTag("a").get(0);
            }
            catch (NullPointerException | IndexOutOfBoundsException e)
            {
                log.error("Can't found item element by attribute!", e);
                continue;
            }
            // id
            String id = String.valueOf(++i);

            // url
            String infoUrl = itemElement.attr("href");

            // title
            String infoTitle = itemElement.html();

            list.add(new Info(id, infoTitle, infoUrl));
        }
        log.debug("return list size is {}.", list.size());
        return list;
    }
}
