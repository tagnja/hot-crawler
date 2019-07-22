package com.taogen.hotcrawler.api.service;

import com.taogen.hotcrawler.commons.crawler.impl.V2exHotProcessor;
import com.taogen.hotcrawler.commons.crawler.impl.ZhihuHotProcessor;
import com.taogen.hotcrawler.commons.entity.db.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoService
{
    private Logger log = LoggerFactory.getLogger(InfoService.class);

    @Autowired
    private V2exHotProcessor v2exHotProcessor;

    @Autowired
    private ZhihuHotProcessor zhihuHotProcessor;

    @Autowired
    private InfoRepository infoRepository;

    public List<Info> findListByTypeId(String typeId)
    {
        List<Info> infoList = infoRepository.findByTypeId(typeId);
        if (infoList == null || infoList.size() <= 0)
        {
            infoList = crawlHotList(typeId);
            // update to redis
            if (infoList != null && infoList.size() > 0)
            {
                infoRepository.saveAll(infoList, typeId);
            }
        }
        return infoList;
    }

    private List<Info> crawlHotList(String typeId)
    {
        List<Info> hotList = new ArrayList<>();
        if (v2exHotProcessor.getSITE_ID().equals(typeId))
        {
            hotList = v2exHotProcessor.crawlHotList();
        }
        else if (zhihuHotProcessor.getSITE_ID().equals(typeId))
        {
            hotList = zhihuHotProcessor.crawlHotList();
        }
        return hotList;
    }
}
