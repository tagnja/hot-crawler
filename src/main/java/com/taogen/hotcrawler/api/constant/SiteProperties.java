package com.taogen.hotcrawler.api.constant;

import com.taogen.hotcrawler.commons.entity.InfoCate;
import com.taogen.hotcrawler.commons.entity.InfoType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties(SiteProperties.class)
@PropertySource("classpath:sites.properties")
@ConfigurationProperties
public class SiteProperties
{

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
                        cate.getSites().forEach(site -> { infoTypes.add(new InfoType(site.getId(), site.getName()));});
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
                    siteCate.getSites().forEach(siteInfo -> { siteInfos.add(siteInfo);});
                }
            }
        }
        return siteInfos;
    }
    public SiteInfo findByProcessorName(String proceesorName)
    {
        if (this.cates != null)
        {
            for (SiteCate siteCate : this.cates)
            {
                if (siteCate.getSites() != null)
                {
                    for (SiteInfo siteInfo : siteCate.getSites())
                    {
                        if (siteInfo.getProcessorName().equals(proceesorName))
                        {
                            return siteInfo;
                        }
                    }
                }
            }
        }
        return null;
    }
}
