package com.taogen.hotcrawler.commons.crawler.handler.impl;

import com.taogen.hotcrawler.commons.crawler.handler.DataHandler;
import com.taogen.hotcrawler.commons.entity.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DeduplicationDataHandler extends DataHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<Info> handleRequest(List<Info> infoList) {
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
            if (info != null) {
                if (infoUrlSet.contains(info.getUrl())) {
                    subtract++;
                    continue;
                }
                infoUrlSet.add(info.getUrl());
                info.setId(String.valueOf(i + 1 - subtract));
                resultList.add(info);
            }
        }
        return resultList;
    }
}
