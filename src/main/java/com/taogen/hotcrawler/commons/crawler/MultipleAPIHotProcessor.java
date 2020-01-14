package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.vo.HttpRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class MultipleAPIHotProcessor extends AbstractHotProcessor{

    protected List<HttpRequest> httpRequestList;

    protected abstract List<Info> getInfoListByJson(String json, int index);

    @Override
    public List<Info> crawlHotList() {
        List<Info> infoList = getHotInfoListByHttpRequestParams(getHttpRequestList());
        return getHandlerCenter().handleData(infoList);
    }

    protected List<Info> getHotInfoListByHttpRequestParams(List<HttpRequest> httpRequestList){
        List<Info> returnInfoList = new ArrayList<>();
        if (httpRequestList != null) {
            for (int i = 0; i < httpRequestList.size(); i++) {
                String json = getJson(httpRequestList.get(i));
                List<Info> infoList = getInfoListByJson(json, i);
                returnInfoList.addAll(infoList);
            }
        }
        return returnInfoList;
    }
}
