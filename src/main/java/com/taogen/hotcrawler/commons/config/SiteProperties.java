package com.taogen.hotcrawler.commons.config;

import com.taogen.hotcrawler.commons.entity.InfoCate;
import com.taogen.hotcrawler.commons.entity.InfoType;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Configuration
@EnableConfigurationProperties(SiteProperties.class)
@PropertySource("classpath:sites.properties")
@ConfigurationProperties
public class SiteProperties
{
    Logger log = LoggerFactory.getLogger(SiteProperties.class);

    List<SiteCate> cates;

    @Data
    public static class SiteCate
    {
        private String id;
        private String name;
        private List<SiteInfo> sites;
    }

    @Data
    public static class SiteInfo
    {
        private String id;
        private String name;
        private String processorName;
        private String url;
        private String prefix;
    }

    @PostConstruct
    public void argumentsCheck(){
        log.debug("begin argument check in property file...");
        // Is url right? Does processName exist?
        // TODO
    }

    public List<InfoCate> convertToInfoCateList()
    {
        List<InfoCate> infoCates = new ArrayList<>();
            if (this.cates != null)
            {
                for (SiteCate cate : cates)
                {
                    List<InfoType> infoTypes = new ArrayList<>();
                    if (cate.getSites() != null)
                    {
                        cate.getSites().forEach(site -> infoTypes.add(new InfoType(site.getId(), site.getName())));
                    }
                    infoCates.add(new InfoCate(cate.getId(), cate.getName(), infoTypes));
                }
        }
        return infoCates;
    }

    public List<SiteInfo> sites()
    {
        List<SiteInfo> siteInfos = new ArrayList<>();
        if (this.cates != null)
        {
            for (SiteCate siteCate : cates)
            {
                if (siteCate.getSites() != null)
                {
                    siteInfos.addAll(siteCate.getSites().stream().collect(Collectors.toList()));
                }
            }
        }
        return siteInfos;
    }

    public SiteInfo findByCateIdAndTypeId(String cateId, String typeId)
    {
        if (this.cates == null)
        {
            return null;
        }
        for (SiteCate siteCate : this.cates)
        {
            if (siteCate.getSites() != null && siteCate.getId().equals(cateId))
            {
                for (SiteInfo siteInfo : siteCate.getSites())
                {
                    if (siteInfo.getId().equals(typeId))
                    {
                        return siteInfo;
                    }
                }
            }
        }
        return null;
    }
}
