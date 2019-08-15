package com.taogen.hotcrawler.commons.crawler.impl.stream;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("CloudmusicHotProcessor")
public class CloudmusicHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(CloudmusicHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://music.163.com";
    public static final String HOT_PAGE_URL = "https://music.163.com/discover/toplist?id=19723756";
    public static final String ITEM_KEY = "song-list-pre-cache";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "music.163.com");
        headers.put("Referer", "https://music.163.com/");
        Document doc = baseHotProcessor.getDoc(HOT_PAGE_URL, headers, log);
        if (doc == null)
        {
            return list;
        }

        // elements
        Elements elements = null;
        Element contentElement = doc.getElementById(ITEM_KEY);
        if (contentElement != null)
        {
            elements = contentElement.getElementsByTag("li");
        }
        if (elements == null)
        {
            return list;
        }

        log.debug("elements size is {}", elements.size());

        int i = 0;
        for (Element element : elements)
        {
            Element itemElement = null;
            try
            {
                itemElement = element.getElementsByTag("a").get(0);
            }
            catch (NullPointerException | IndexOutOfBoundsException e)
            {
                log.error("Can't found item element by attribute!", e);
                continue;
            }

            if (itemElement != null)
            {
                // id
                String id = String.valueOf(++i);

                // url
                StringBuilder infoUrl = new StringBuilder();
                infoUrl.append(DOMAIN);
                infoUrl.append("#");
                infoUrl.append(itemElement.attr("href"));

                // title
                String infoTitle = itemElement.html();

                list.add(new Info(id, infoTitle, infoUrl.toString()));
            }
        }

        return baseHotProcessor.handleData(list);
    }
}
