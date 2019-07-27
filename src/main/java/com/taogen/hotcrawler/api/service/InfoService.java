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

    public List<Info> findListByCateIdAndTypeId(String cateId, String typeId)
    {
        List<Info> infoList = infoRepository.findByCateIdAndTypeId(cateId, typeId);
        log.debug("info list size: " + infoList.size());
        if (infoList == null || infoList.size() <= 0)
        {
            infoList = crawlHotList(cateId, typeId);
            // update to redis
            if (infoList != null && infoList.size() > 0)
            {
                infoRepository.saveAll(infoList, cateId, typeId);
            }
        }
        return infoList;
    }

    private List<Info> crawlHotList(String cateId, String typeId)
    {
        List<Info> hotList = new ArrayList<>();
        HotProcessor hotProcess = null;
        List<SiteProperties.SiteCate> cateList = siteProperties.getCates();
        if (cateList != null)
        {
            for (SiteProperties.SiteCate cate : cateList)
            {
                if (cate.getId().equals(cateId))
                {
                    if (cate.getSites() != null)
                    {
                        for (SiteProperties.SiteInfo site : cate.getSites())
                        {
                            if (site.getId().equals(typeId))
                            {
                                hotProcess = (HotProcessor) getBean(site.getProcessorName());
                                break;
                            }
                        }
                    }
                }
            }
            log.debug("hot process: " + hotProcess.getClass().getSimpleName());
        }
        if (hotProcess != null)
        {
            hotList = hotProcess.crawlHotList();
        }
        return hotList;
    }
}
