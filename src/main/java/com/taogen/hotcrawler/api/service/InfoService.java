package com.taogen.hotcrawler.api.service;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InfoService extends BaseService
{
    public static final Logger log = LoggerFactory.getLogger(InfoService.class);

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private SiteProperties siteProperties;

    private final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

    public List<Info> findListByCateIdAndTypeId(String cateId, String typeId)
    {
        List<Info> infoList = infoRepository.findByCateIdAndTypeId(cateId, typeId);
        if (infoList == null || infoList.isEmpty())
        {
            infoList = crawlHotList(cateId, typeId);
            // update to redis
            if (infoList != null && ! infoList.isEmpty())
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
        SiteProperties.SiteInfo site = siteProperties.findByCateIdAndTypeId(cateId, typeId);
        hotProcess = (HotProcessor) getBean(site.getProcessorName());
        if (hotProcess != null)
        {
            hotList = hotProcess.crawlHotList();
        }
        return hotList;
    }

    public void statVisitUser(HttpServletRequest request)
    {
        String ip = request.getRemoteAddr();
        String today = dateFormatter.format(new Date());
        infoRepository.statVisitUser(ip, today);
    }

    public long countVisitUser()
    {
        String today = dateFormatter.format(new Date());
        return infoRepository.countVisitUser(today);
    }
}
