package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class APIHotProcessor extends AbstractHotProcessor {

    protected abstract List<Info> getInfoDataByJson(String json);

    @Override
    public List<Info> crawlHotList() {
        return crawlHotListFromAPI();
    }

    private List<Info> crawlHotListFromAPI(){
        List<Info> infoList = new ArrayList<>();
        String json = getJson(this.httpRequest);
        if (json != null){
            infoList = getInfoDataByJson(json);
        }
        log.debug("crawl hot list from {}, list size is {}", this.name, infoList.size());
        return handlerCenter.handleData(infoList);
    }

    @Override
    protected Map<String, String> generateHeader(){
        // Basic implementation, most situation not need header
        return null;
    }

    @Override
    protected String generateRequestBody(){
        // Basic implementation, most situation not need request body
        return null;
    }
}
