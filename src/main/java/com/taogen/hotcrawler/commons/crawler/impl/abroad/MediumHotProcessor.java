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

@Component("MediumHotProcessor")
public class MediumHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(MediumHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://medium.com";
    public static final String HOT_PAGE_URL = "https://medium.com/topic/popular";
    public static final String ITEM_KEY = "v w x gl z l";

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
                Element item = element.getElementsByClass("aq ai eb br ec bs fe gs gt as av fg eh ei au").
                        get(0).getElementsByTag("a").get(0);
                String infoTitle = item.html();
                StringBuilder infoUrl = new StringBuilder();
                infoUrl.append(item.attr("href"));
                String url = infoUrl.substring(0, infoUrl.indexOf("?"));
                String id = String.valueOf(++i);
                list.add(new Info(id, infoTitle, url));
            }
            catch(IndexOutOfBoundsException e)
            {
                log.error("Can't find attribute!", e);
            }
        }

        return baseHotProcessor.handleData(list);
    }
}
