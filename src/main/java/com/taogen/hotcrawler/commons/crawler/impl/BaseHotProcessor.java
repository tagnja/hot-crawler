package com.taogen.hotcrawler.commons.crawler.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class BaseHotProcessor
{
    public Document getDoc(String url, Map<String, String> headers, Logger log)
    {
        Document doc = null;
        Connection connection = Jsoup.connect(url);
        addBasicHeaders(connection);
        if (headers != null)
        {
            connection.headers(headers);
        }
        try
        {
            doc = connection.timeout(10 * 1000).get();
        }
        catch (IOException e)
        {
            log.error("Fail to connect!", e);
        }
        return doc;
    }

    public String getJson(String url, Logger log)
    {
        String json = null;
        try
        {
            json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        }
        catch (IOException e)
        {
            log.error("Fail to connect the website!", e);
        }
        return json;
    }

    private void addBasicHeaders(Connection connection)
    {
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "en-US,en;q=0.5");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
    }
}
