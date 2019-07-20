package com.taogen.hotcrawler.crawler;

import com.taogen.hotcrawler.entity.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class V2exPageProcessor implements PageProcess
{
    public static final String V2EX_DOMAIN = "https://v2ex.com";
    public static final String PAGE_HOT = "/?tab=hot";
    public static final String ITEM_KEY = "item_title";

    @Override
    public List<Info> getHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = null;
        try {
            doc = Jsoup.connect(V2EX_DOMAIN + PAGE_HOT).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // elements
        Elements elements = doc.getElementsByClass(ITEM_KEY);

        int i = 0;
        for (Element element : elements)
        {
            Elements elements1 = element.getElementsByTag("a");
            String infoTitle = elements1.html();
            String infoUrl = V2EX_DOMAIN + elements1.attr("href");
            String id = String.valueOf(++i);
            list.add(new Info(id, infoTitle, infoUrl));
        }
        return list;
    }
}
