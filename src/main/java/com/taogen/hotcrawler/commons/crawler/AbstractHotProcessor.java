package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.handler.HandlerCenter;
import com.taogen.hotcrawler.commons.entity.Info;
import lombok.Data;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractHotProcessor implements HotProcessor
{
    private static final int DOMAIN_INDEX_SPAN = 2;
    protected String name;
    protected String url;
    protected String prefix;
    protected Map<String, String> header;
    protected String requestBody;
    protected RequestMethod requestMethod;
    protected Logger log;
    protected HandlerCenter handlerCenter;

    public abstract List<Info> crawlHotList();
    protected abstract void initialize();
    protected abstract Map<String, String> generateHeader();
    protected abstract String generateRequestBody();

    protected void injectBeans(ApplicationContext applicationContext){
        this.handlerCenter = applicationContext.getBean(HandlerCenter.class);
//        log.debug("site properties inject ------------ is {}", applicationContext.getBean(SiteProperties.class));
    }

    protected void setFieldsByProperties(SiteProperties siteProperties){
        List<SiteProperties.SiteInfo> sites = siteProperties.sites();
        if (sites != null) {
            for (SiteProperties.SiteInfo site : sites) {
                if (this.getClass().getSimpleName().equals(site.getProcessorName())){
                    setName(site.getName());
                    setUrl(site.getUrl());
                    setPrefix(site.getPrefix());
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
}
