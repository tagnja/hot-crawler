package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
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

@Component("InfoqcomHotProcessor")
public class InfoqcomHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(InfoqcomHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://www.infoq.com";
    public static final String HOT_PAGE_URL = "https://www.infoq.com/news/";
    public static final String ITEM_KEY = "card__content";

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
        try
        {
            Elements elements = doc.getElementsByClass("items__content").get(0).getElementsByClass(ITEM_KEY);
            int i = 0;
            for (Element element : elements)
            {
                element = element.getElementsByClass("card__title").get(0).getElementsByTag("a").get(0);
                // title
                String infoTitle = element.html();
                // url
                StringBuilder infoUrl = new StringBuilder();
                infoUrl.append(DOMAIN);
                infoUrl.append(element.attr("href"));
                String id = String.valueOf(++i);
                list.add(new Info(id, infoTitle, infoUrl.toString()));
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            log.error("Can't find attribute!", e);
        }

        return baseHotProcessor.handleData(list);
    }
}
