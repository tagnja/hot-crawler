package com.taogen.hotcrawler.commons.crawler;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.taogen.hotcrawler.commons.entity.Info;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class SimpleAPIHotProcessor extends APIHotProcessor {
    private List<String> titleJsonPaths;
    private List<String> urlJsonPaths;

    @Override
    protected List<Info> getInfoDataByJson(String json) {
        List<Info> infoList = new ArrayList<>();
        if (json != null && checkJsonPathList(this.titleJsonPaths, this.urlJsonPaths)){
            for (int i = 0; i < titleJsonPaths.size(); i++){
                try {
                    List<String> titles = JsonPath.read(json, this.titleJsonPaths.get(i));
                    List<String> urls = JsonPath.read(json, this.urlJsonPaths.get(i));
                    infoList.addAll(getInfoListByTitlesAndUrls(titles, urls));
                }catch(PathNotFoundException e){
                    log.error("Json path error!", e);
                }
            }
        }
        return infoList;
    }

    private boolean checkJsonPathList(List<String> titleJsonPaths, List<String> urlJsonPaths){
        return titleJsonPaths != null && urlJsonPaths != null && titleJsonPaths.size() == urlJsonPaths.size();
    }

}
