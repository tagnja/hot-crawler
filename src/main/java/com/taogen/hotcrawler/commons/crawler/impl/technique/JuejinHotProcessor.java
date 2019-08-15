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

@Component("JuejinHotProcessor")
public class JuejinHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(JuejinHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://juejin.im";
    public static final String HOT_PAGE_URL = "https://juejin.im";
    public static final String ITEM_KEY = "title-row";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = baseHotProcessor.getDocByWebDriver(HOT_PAGE_URL, log);
        if (doc == null)
        {
            return list;
        }

        // elements
        Elements elements = doc.getElementsByClass(ITEM_KEY);

        int i = 0;
        for (Element element : elements)
        {
            Element itemElement = element.getElementsByTag("a").get(0);
            // title
            String infoTitle = itemElement.html();

            // url
            StringBuilder infoUrl = new StringBuilder();
            infoUrl.append(DOMAIN);
            infoUrl.append(itemElement.attr("href"));
            // id
            String id = String.valueOf(++i);
            list.add(new Info(id, infoTitle, infoUrl.toString()));
            if (i == 50)
            {
                break;
            }
        }

        return baseHotProcessor.handleData(list);
    }
}
