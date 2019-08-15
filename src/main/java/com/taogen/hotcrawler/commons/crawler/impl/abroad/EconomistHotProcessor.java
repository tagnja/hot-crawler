package com.taogen.hotcrawler.commons.crawler.impl.abroad;

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

@Component("EconomistHotProcessor")
public class EconomistHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(EconomistHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://www.economist.com";
    public static final String HOT_PAGE_URL = "https://www.economist.com/latest/";
    public static final String ITEM_KEY = "teaser";

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

        int i = 0;
        for (Element element : elements)
        {
            try
            {
                Element item = element.getElementsByClass("teaser__link").get(0);
                String infoTitle = item.attr("aria-label");
                StringBuilder infoUrl = new StringBuilder();
                infoUrl.append(DOMAIN);
                infoUrl.append(item.attr("href"));
                String id = String.valueOf(++i);
                list.add(new Info(id, infoTitle, infoUrl.toString()));
            }
            catch(IndexOutOfBoundsException e)
            {
                log.error("Can't find attribute!", e);
            }
        }

        return baseHotProcessor.handleData(list);
    }
}
