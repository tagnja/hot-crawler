package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.handler.HandlerCenter;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.vo.HttpRequest;
import lombok.Data;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractHotProcessor implements HotProcessor
{
    private static final int DOMAIN_INDEX_SPAN = 2;
    protected String name;
    protected String prefix;
    protected HttpRequest httpRequest;
    protected Logger log;
    protected HandlerCenter handlerCenter;

    protected abstract void initialize();
    protected abstract Map<String, String> generateHeader();
    protected abstract String generateRequestBody();

    protected void injectBeansByContext(ApplicationContext applicationContext){
        this.handlerCenter = applicationContext.getBean(HandlerCenter.class);
    }

    protected void setFieldsByProperties(SiteProperties siteProperties, RequestMethod requestMethod, Map<String, String> header, String requestBody){
        if (getHttpRequest() == null){
            setHttpRequest(new HttpRequest());
        }
        getHttpRequest().setRequestMethod(requestMethod);
        getHttpRequest().setHeader(header);
        getHttpRequest().setRequestBody(requestBody);
        List<SiteProperties.SiteInfo> sites = siteProperties.sites();
        if (sites != null) {
            for (SiteProperties.SiteInfo site : sites) {
                if (this.getClass().getSimpleName().equals(site.getProcessorName())){
                    setName(site.getName());
                    getHttpRequest().setUrl(site.getUrl());
                    setPrefix(site.getPrefix());
                    break;
                }
            }
        }
    }

    protected Connection.Method getJsoupRequestMethod(RequestMethod requestMethod){
        if (RequestMethod.POST.equals(requestMethod)){
            return Connection.Method.POST;
        }else{
            return Connection.Method.GET;
        }
    }

    protected void addBasicHeaders(Connection connection)
    {
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "en-US,en;q=0.5");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
    }

    protected List<String> urlsAddPrefix(String prefix, List<String> urls)
    {
        if (urls == null)
        {
            return urls;
        }

        for (int i = 0; i < urls.size(); i++)
        {
            urls.set(i, prefix + urls.get(i));
        }

        return urls;
    }

    protected List<Info> getInfoListByTitlesAndUrls(List<String> titles, List<String> urls)
    {
        List<Info> infoList = new ArrayList<>();
        if (titles == null || urls == null)
        {
            return infoList;
        }

        for (int i = 0; i < titles.size(); i++)
        {
            infoList.add(new Info(String.valueOf(i+1), titles.get(i), urls.get(i)));
        }
        return infoList;
    }

    protected Document getDocument(HttpRequest httpRequest){
        Document doc = null;
        Connection connection = Jsoup.connect(httpRequest.getUrl());
        if (httpRequest.getHeader() != null) {
            addBasicHeaders(connection);
            connection.headers(httpRequest.getHeader());
        }
        try {
            doc = connection.timeout(10 * 1000).get();
        } catch (IOException e) {
            log.error("Fail to connect!", e);
        }
        return doc;
    }

    protected String getJson(HttpRequest httpRequest){
        String json = null;
        try {
            if (RequestMethod.POST.equals(httpRequest.getRequestMethod())) {
                json = Jsoup.connect(httpRequest.getUrl()).ignoreContentType(true).headers(httpRequest.getHeader()).
                        requestBody(httpRequest.getRequestBody()).method(getJsoupRequestMethod(httpRequest.getRequestMethod())).execute().body();
            }else {
                json = Jsoup.connect(httpRequest.getUrl()).ignoreContentType(true).execute().body();
            }
        } catch (IOException e) {
            log.error("Fail to connect the website!", e);
        }
        return json;
    }
}
