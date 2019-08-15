package com.taogen.hotcrawler.commons.crawler.impl.slack;

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

@Component("WeiboHotProcessor")
public class WeiboHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(WeiboHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://s.weibo.com";
    public static final String HOT_PAGE_URL = "https://s.weibo.com/top/summary?cate=realtimehot";
    public static final String ITEM_KEY = "tr";

    @Override
    public List<Info> crawlHotList() {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = baseHotProcessor.getDoc(HOT_PAGE_URL, null, log);
        if (doc == null)
        {
            return list;
        }

        // elements
        Elements elements = doc.getElementsByTag(ITEM_KEY);
        // remove two tr elements
        elements.remove(0);
        elements.remove(0);

        int i = 0;
        for (Element element : elements)
        {
            Element itemElement = element.getElementsByClass("td-02").get(0).getElementsByTag("a").get(0);

            // id
            String id = String.valueOf(++i);

            // url
            String infoUrl = itemElement.attr("href");

            // title
            String infoTitle =  itemElement.html();

            infoUrl = DOMAIN + infoUrl;
            list.add(new Info(id, infoTitle, infoUrl));
        }

        return baseHotProcessor.handleData(list);
    }
}
