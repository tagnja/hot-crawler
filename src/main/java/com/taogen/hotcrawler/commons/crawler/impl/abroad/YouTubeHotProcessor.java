package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.BaseHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("YouTubeHotProcessor")
public class YouTubeHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(YouTubeHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://www.youtube.com";
    public static final String HOT_PAGE_URL = "https://www.youtube.com/feed/trending";
    public static final String ITEM_KEY = "title-and-badge";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = null;
        Connection connection = Jsoup.connect(HOT_PAGE_URL);
        try
        {
            doc = connection.timeout(10 * 1000).get();
        }
        catch (IOException e)
        {
            log.error("Fail to connect!", e);
        }
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
                Element item = element.getElementsByTag("a").get(0);
                String infoTitle = item.html();
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
