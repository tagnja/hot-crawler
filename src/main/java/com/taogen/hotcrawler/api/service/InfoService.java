package com.taogen.hotcrawler.api.service;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoService extends BaseService
{
    private Logger log = LoggerFactory.getLogger(InfoService.class);

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private SiteProperties siteProperties;

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
        HotProcessor hotProcess = null;
        List<SiteProperties.SiteInfo> siteList = siteProperties.getSites();
        if (siteList != null)
        {
            for (SiteProperties.SiteInfo site : siteList)
            {
                if (site.getId().equals(typeId))
                {
                    hotProcess = (HotProcessor) getBean(site.getProcessorName());
                    break;
                }
            }
        }
        if (hotProcess != null)
        {
            hotList = hotProcess.crawlHotList();
        }
        return hotList;
    }
}
