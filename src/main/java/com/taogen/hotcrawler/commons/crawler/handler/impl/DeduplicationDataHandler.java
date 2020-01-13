package com.taogen.hotcrawler.commons.crawler.handler.impl;

import com.taogen.hotcrawler.commons.crawler.handler.DataHandler;
import com.taogen.hotcrawler.commons.entity.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DeduplicationDataHandler extends DataHandler {

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
