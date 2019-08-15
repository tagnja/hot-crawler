package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.commons.entity.Info;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
public class BaseHotProcessor
{
    public static final Logger log = LoggerFactory.getLogger(BaseHotProcessor.class);

//    WebDriver driver;

//    @PostConstruct
//    public void init()
//    {
//        try
//        {
//            ChromeDriverManager.chromedriver().setup();
//            WebDriverManager.chromedriver().config().setProperties("classpath: webdrivermanager.properties");
//            driver = new ChromeDriver();
//        }
//        catch (RuntimeException e)
//        {
//            log.error("Something error!", e);
//        }
//    }

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

    public Document getDocByWebDriver(String hotPageUrl, Logger log)
    {
        Document doc = null;
        try
        {
            ChromeDriverManager.chromedriver().setup();
            WebDriverManager.chromedriver().config().setProperties("classpath: webdrivermanager.properties");
            WebDriver driver = new ChromeDriver();
            driver.get(hotPageUrl);
            Thread.sleep(2 * 1000L);
            String pageSource = driver.getPageSource();
            doc = Jsoup.parse(pageSource);
            driver.close();
        }
        catch (InterruptedException | RuntimeException  e)
        {
            log.error("Web driver occur error!", e);
        }
        return doc;
    }

    public List<Info> handleData(List<Info> infoList)
    {
        return removeRepeat(infoList);
    }

    private List<Info> removeRepeat(List<Info> infoList)
    {
        List<Info> resultList = new ArrayList<>();
        if (infoList == null)
        {
            return resultList;
        }

        Set<String> infoUrlSet = new HashSet<>();
        int subtract = 0;
        for (int i = 0; i < infoList.size(); i++)
        {
            Info info = infoList.get(i);
            if (infoUrlSet.contains(info.getUrl()))
            {
                subtract++;
                continue;
            }

            infoUrlSet.add(info.getUrl());
            info.setId(String.valueOf(i + 1 - subtract));
            resultList.add(info);
        }
        return resultList;
    }
}
