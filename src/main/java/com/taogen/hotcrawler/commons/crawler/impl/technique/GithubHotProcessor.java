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

@Component("GithubHotProcessor")
public class GithubHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(GithubHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://github.com";
    public static final String HOT_PAGE_URL = "https://github.com/trending";
    public static final String ITEM_KEY = "Box-row";

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
            // id
            String id = String.valueOf(++i);
            Element urlElement = null;
            Element descElement = null;

            try
            {
                // url
                urlElement = element.getElementsByTag("h1").get(0).getElementsByTag("a").get(0);
                // title-desc
                if (! element.getElementsByTag("p").isEmpty())
                {
                    descElement = element.getElementsByTag("p").get(0);
                }
            }
            catch (NullPointerException | IndexOutOfBoundsException e)
            {
                log.error("Can't found item element by attribute!", e);
                log.debug("index of error element is {}.", i);
                continue;
            }

            String infoUrl = urlElement.attr("href");
            String infoTitle = infoUrl.substring(infoUrl.indexOf('/', 1) + 1) + ". ";
            String desc = descElement == null ? "" : descElement.html();
            infoTitle = infoTitle + desc;
            infoUrl = DOMAIN + infoUrl;
            list.add(new Info(id, infoTitle, infoUrl));
        }

        return baseHotProcessor.handleData(list);
    }
}
