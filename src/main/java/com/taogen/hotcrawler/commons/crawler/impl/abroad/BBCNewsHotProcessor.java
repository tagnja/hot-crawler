package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.technique.DeveloperHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("BBCNewsHotProcessor")
public class BBCNewsHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(BBCNewsHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://www.bbc.com";
    public static final String HOT_PAGE_URL = "https://www.bbc.com/news";
    public static final String ITEM_KEY = "gs-c-promo-heading";
    //  nw-o-link-split__anchor block: .nw-c-top-stories--standard, title: .gs-c-promo-heading__title,url: .gs-c-promo-heading

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

        Elements elements = doc.getElementsByClass("nw-c-top-stories--standard").get(0).getElementsByClass("gs-c-promo-heading");
        Map<String, Info> infoMap = new HashMap<>();
        int i = 0;
        for (Element element : elements)
        {
            try
            {
                String infoTitle = element.getElementsByClass("gs-c-promo-heading__title").html();
                StringBuilder infoUrl = new StringBuilder();
                infoUrl.append(DOMAIN);
                infoUrl.append(element.attr("href"));
                String id = String.valueOf(++i);
                infoMap.put(infoUrl.toString(), new Info(id, infoTitle, infoUrl.toString()));
            }
            catch(IndexOutOfBoundsException e)
            {
                log.error("Can't find attribute!", e);
            }
        }
        infoMap.forEach((key,value) -> list.add(value));
        log.debug("return list size is {}", list.size());
        return list;
    }
}
