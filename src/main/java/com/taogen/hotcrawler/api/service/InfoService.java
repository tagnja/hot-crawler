package com.taogen.hotcrawler.api.service;

import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.repository.InfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InfoService extends BaseService
{
    public static final Logger log = LoggerFactory.getLogger(InfoService.class);

    @Autowired
    private InfoRepository infoRepository;

    /*@Autowired
    private SiteProperties siteProperties;*/

    private final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

    public List<Info> findListByCateIdAndTypeId(String cateId, String typeId)
    {
        List<Info> infoList = infoRepository.findByCateIdAndTypeId(cateId, typeId);
        return infoList;
    }


    public void statVisitUser(HttpServletRequest request)
    {
        String ip = getRealIpAddress(request);
        String today = dateFormatter.format(new Date());
        infoRepository.statVisitUser(ip, today);
    }

    public long countVisitUser()
    {
        String today = dateFormatter.format(new Date());
        return infoRepository.countVisitUser(today);
    }

    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getRealIpAddress(HttpServletRequest request)
    {
        String ip = getIpAddr(request);
        int ipIndex = ip.indexOf(IP_HEADER);
        if (ipIndex >= 0)
        {
            return IP_ADDRESS;
        }
        return ip;
    }

    private static final String IP_ADDRESS = "58.212.237.176";
    private static final String IP_HEADER = "192.168.";

}
