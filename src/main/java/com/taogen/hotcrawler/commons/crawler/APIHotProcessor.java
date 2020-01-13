package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class APIHotProcessor extends AbstractHotProcessor {

    protected abstract List<Info> getInfoDataByJson(String json);

    @Override
    public List<Info> crawlHotList() {
        return crawlHotListFromAPI();
    }

    private List<Info> crawlHotListFromAPI(){
        List<Info> infoList = new ArrayList<>();
        String json = getJson();
        if (json != null){
            infoList = getInfoDataByJson(json);
        }
        log.debug("crawl hot list from {}, list size is {}", this.name, infoList.size());
        return handlerCenter.handleData(infoList);
    }

    protected String getJson(){
        String json = null;
        try {
            if (RequestMethod.POST.equals(this.requestMethod)){
                json = Jsoup.connect(this.url).ignoreContentType(true).headers(this.header).
                        requestBody(this.requestBody).method(getJsoupRequestMethod(this.requestMethod)).execute().body();
            }else {
                json = Jsoup.connect(this.url).ignoreContentType(true).execute().body();
            }
        } catch (IOException e) {
            log.error("Fail to connect the website!", e);
        }
        return json;
    }
}
